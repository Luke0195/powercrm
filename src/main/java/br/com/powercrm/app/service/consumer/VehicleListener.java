package br.com.powercrm.app.service.consumer;

import br.com.powercrm.app.domain.entities.Brand;
import br.com.powercrm.app.domain.entities.Model;
import br.com.powercrm.app.domain.entities.User;
import br.com.powercrm.app.domain.entities.Vehicle;
import br.com.powercrm.app.dto.response.VehicleEventDto;
import br.com.powercrm.app.external.fipe.OpenFeignFipeClient;
import br.com.powercrm.app.external.fipe.dtos.*;
import br.com.powercrm.app.repository.UserRepository;
import br.com.powercrm.app.repository.VehicleRepository;
import br.com.powercrm.app.service.FipeService;
import br.com.powercrm.app.service.validators.FipeValidation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;


import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

@Component
@Slf4j
@AllArgsConstructor
public class VehicleListener {

    private final FipeService fipeService;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final FipeValidation fipeValidation;
    private final OpenFeignFipeClient openFeignFipeClient;

    @RabbitListener(queues = {"vehicle_creation_queue"})
    public void consumerVehicleQueue(VehicleEventDto vehicleEventDto) {
        try {
            FipeMarcaResponse marca = openFeignFipeClient.getMarcas()
                    .stream()
                    .filter(m -> m.getCodigo().equals(String.valueOf(vehicleEventDto.brandId())))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Marca não encontrada na FIPE"));

            // 2. Validar modelo
            FipeModeloResponse modelo = openFeignFipeClient.getModelos(marca.getCodigo())
                    .getModelos()
                    .stream()
                    .filter(x -> x.getCodigo().equalsIgnoreCase(vehicleEventDto.modelId().toString()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Modelo não encontrado na FIPE"));

            // 3. Validar ano
            List<FipeAnosResponse> anos = fipeService.getAnos(marca.getCodigo(), modelo.getCodigo());
            String anoCodigo = anos.stream()
                    .filter(a -> a.getCodigo().startsWith(vehicleEventDto.year().toString()))
                    .map(FipeAnosResponse::getCodigo)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Ano não encontrado na FIPE"));

            log.info("Veículo encontrado na FIPE: marca={}, modelo={}, anoCodigo={}", marca.getNome(), modelo.getNome(), anoCodigo);

            // 4. Obter valor FIPE
            Map<String, Object> fipeValorResponse = openFeignFipeClient.getValor(marca.getCodigo(), modelo.getCodigo(), anoCodigo);
            if (Objects.isNull(fipeValorResponse)) {
                throw new RuntimeException("Erro ao obter o valor do veículo na FIPE");
            }

            String valorFipe = ((String) fipeValorResponse.get("Valor"))
                    .replace("R$ ", "")
                    .replace(".", "")
                    .replace(",", ".");
            BigDecimal fipePrice = BigDecimal.valueOf(Double.parseDouble(valorFipe));

            // 5. Buscar usuário
            User user = userRepository.findById(vehicleEventDto.userId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            Optional<Vehicle> vehicleAlreadyExists = vehicleRepository.findByPlate(vehicleEventDto.plate());

            // 6. Verificar duplicidade da placa
            if (vehicleAlreadyExists.isPresent()) {
                log.warn("Placa já cadastrada: {}", vehicleAlreadyExists.get().getPlate());
                return; // Envia para a DLQ
            }

            // 7. Montar e salvar veículo
            Vehicle vehicle = new Vehicle();
            vehicle.setPlate(vehicleEventDto.plate());
            vehicle.setAdvertisedPlate(vehicleEventDto.advertisedPlate());
            vehicle.setUser(user);
            vehicle.setBrand(Brand.builder()
                    .name(marca.getNome())
                    .brandCode(marca.getCodigo())
                    .build());
            vehicle.setModel(Model.builder()
                    .name(modelo.getNome())
                    .modelCode(modelo.getCodigo())
                    .build());
            vehicle.setVehicleYear(vehicleEventDto.year());
            vehicle.setFipePrice(fipePrice);

            vehicleRepository.save(vehicle);
            log.info("Veículo validado e salvo com sucesso: {}", vehicle.getPlate());

        } catch (AmqpRejectAndDontRequeueException ex) {
            // Esse é o fluxo esperado para mensagens inválidas, elas vão para DLQ
            log.error("Erro ao processar mensagem: {}", ex.getMessage(), ex);
            throw ex; // Apenas re-lança a exceção para garantir que a DLQ seja acionada
        } catch (Exception e) {
            // Se outros erros ocorrerem, você pode querer enviar para DLQ também
            log.error("Erro ao processar mensagem da fila: {}", e.getMessage(), e);
            throw new AmqpRejectAndDontRequeueException("Erro ao processar mensagem: " + e.getMessage(), e); // Evita reprocessamento
        }
    }
        private BigDecimal getValue(String apiPrice) throws ParseException {
            NumberFormat numberFormat = NumberFormat.getInstance(new Locale("pt", "BR"));
            Number number = numberFormat.parse(apiPrice.replace("R$", "").trim());
            BigDecimal value = BigDecimal.valueOf(number.doubleValue());
            return value;
        }
    }
