package br.com.powercrm.app.service.validators;


import br.com.powercrm.app.domain.entities.Brand;
import br.com.powercrm.app.domain.entities.Model;
import br.com.powercrm.app.domain.entities.User;
import br.com.powercrm.app.domain.entities.Vehicle;
import br.com.powercrm.app.dto.response.VehicleEventDto;
import br.com.powercrm.app.external.fipe.OpenFeignFipeClient;
import br.com.powercrm.app.external.fipe.dtos.FipeAnosResponse;
import br.com.powercrm.app.external.fipe.dtos.FipeMarcaResponse;
import br.com.powercrm.app.external.fipe.dtos.FipeModeloResponse;
import br.com.powercrm.app.service.FipeService;
import br.com.powercrm.app.service.exceptions.ParseValidationException;
import br.com.powercrm.app.service.exceptions.ThirdPartyServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@AllArgsConstructor
public class FipeValidation {

    private final FipeService fipeService;

    public FipeMarcaResponse getMarca(Long brandId){
        return fipeService.getMarcas()
                .stream()
                .filter(m -> m.getCodigo().equals(String.valueOf(brandId)))
                .findFirst()
                .orElseThrow(() -> new ThirdPartyServiceException("Marca não encontrada na FIPE"));
    }

    public FipeModeloResponse getModelo(String brandId, Long modelId){
     return fipeService.getModelos(brandId)
                .getModelos()
                .stream()
                .filter(x -> x.getCodigo().equalsIgnoreCase(modelId.toString()))
                .findFirst()
                .orElseThrow(() -> new ThirdPartyServiceException("Modelo não encontrado na FIPE"));
    }

    public List<FipeAnosResponse> getYears(String brandId, String modelId){
        return fipeService.getAnos(brandId, modelId);
    }

    public String getCodeYear(List<FipeAnosResponse> data, String year){
        return data.stream()
                .filter(a -> a.getCodigo().startsWith(year))
                .map(FipeAnosResponse::getCodigo)
                .findFirst()
                .orElseThrow(() -> new ThirdPartyServiceException("Ano não encontrado na FIPE"));
    }

    public Map<String, Object> getPrice(String brandId, String modelId, String year){
        return fipeService.getValor(brandId, modelId, year);
    }

    public BigDecimal getFipePrice(Map<String,Object> valor) {
        if(Objects.equals((String) valor.get("valor"), "")) {
            throw new ParseValidationException("O valor price não pode ser vazio");
        }
        String valorFipe = ((String) valor.get("Valor"))
                .replace("R$ ", "")
                .replace(".", "")
                .replace(",", ".");

        return BigDecimal.valueOf(Double.parseDouble(valorFipe));
    }

    public Vehicle makeVehicleWithFipeValues(VehicleEventDto vehicleEventDto, User user,
                                             FipeMarcaResponse fipeMarcaResponse, BigDecimal fipePrice){
        return Vehicle.builder()
                .plate(vehicleEventDto.plate())
                .advertisedPlate(vehicleEventDto.advertisedPlate())
                .user(user)
                .brand(Brand.builder().name(fipeMarcaResponse.getNome()).brandCode(fipeMarcaResponse.getCodigo()).build())
                .model(Model.builder().name(fipeMarcaResponse.getNome()).modelCode(fipeMarcaResponse.getCodigo()).build())
                .vehicleYear(vehicleEventDto.year())
                .fipePrice(fipePrice)
                .build();

    }
}
