package br.com.powercrm.app.service.consumer;

import br.com.powercrm.app.domain.entities.User;
import br.com.powercrm.app.domain.entities.Vehicle;
import br.com.powercrm.app.dto.response.VehicleEventDto;
import br.com.powercrm.app.external.fipe.dtos.*;
import br.com.powercrm.app.repository.UserRepository;
import br.com.powercrm.app.repository.VehicleRepository;

import br.com.powercrm.app.service.exceptions.ThirdPartyServiceException;
import br.com.powercrm.app.service.validators.FipeValidation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;


import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import java.util.*;

@Component
@Slf4j
@AllArgsConstructor
public class VehicleListener {

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final FipeValidation fipeValidation;


    @RabbitListener(queues = {"vehicle_creation_queue"})
    public void consumerVehicleQueue(VehicleEventDto vehicleEventDto) {
        try {
            FipeMarcaResponse marcaFipe = fipeValidation.getMarca(vehicleEventDto.brandId());
            FipeModeloResponse modeloFipe = fipeValidation.getModelo(marcaFipe.getCodigo(), vehicleEventDto.modelId());
            List<FipeAnosResponse> anosFipe = fipeValidation.getYears(marcaFipe.getCodigo(), modeloFipe.getCodigo());
            String anoCodigo = fipeValidation.getCodeYear(anosFipe, vehicleEventDto.year().toString());
            Map<String, Object> fipeValorResponse = fipeValidation.getPrice(marcaFipe.getCodigo(), modeloFipe.getCodigo(), anoCodigo);
            if (Objects.isNull(fipeValorResponse)) {
                throw new RuntimeException("Erro ao obter o valor do veículo na FIPE");
            }
            BigDecimal fipePrice = fipeValidation.getFipePrice(fipeValorResponse);

            User user = userRepository.findById(vehicleEventDto.userId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            Optional<Vehicle> vehicleAlreadyExists = vehicleRepository.findByPlate(vehicleEventDto.plate());

            if (vehicleAlreadyExists.isPresent()) {
                log.warn("Placa já cadastrada: {}", vehicleAlreadyExists.get().getPlate());
                return; // Envia para a DLQ
            }

            Vehicle vehicle =fipeValidation.makeVehicleWithFipeValues(vehicleEventDto, user, marcaFipe, fipePrice);
            vehicleRepository.save(vehicle);
            log.info("Veículo validado e salvo com sucesso: {}", vehicle.getPlate());

        } catch (AmqpRejectAndDontRequeueException e) {
            // Se outros erros ocorrerem, você pode querer enviar para DLQ também
            log.error("Erro ao processar mensagem da fila: {}", e.getMessage(), e);
            throw new AmqpRejectAndDontRequeueException("Erro ao processar mensagem: " + e.getMessage(), e); // Evita reprocessamento
        }
    }

    }
