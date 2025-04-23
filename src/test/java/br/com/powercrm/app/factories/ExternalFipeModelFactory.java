package br.com.powercrm.app.factories;

import br.com.powercrm.app.domain.entities.User;
import br.com.powercrm.app.dto.response.VehicleEventDto;
import br.com.powercrm.app.external.fipe.dtos.FipeAnosResponse;
import br.com.powercrm.app.external.fipe.dtos.FipeMarcaResponse;
import br.com.powercrm.app.external.fipe.dtos.FipeModeloResponse;
import br.com.powercrm.app.external.fipe.dtos.FipeValorResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public class ExternalFipeModelFactory {

    public static FipeMarcaResponse makeMarcaResponse(){
        return FipeMarcaResponse.builder().codigo("1").nome("valid_name").build();
    }

    public static FipeModeloResponse makeModeloResponse(){
        return FipeModeloResponse.builder().codigo("1").nome("valid_name").build();
    }

    public static FipeAnosResponse makeFipeAnosResponse(){
        return  FipeAnosResponse.builder().codigo("2014-4").nome("2014 Diesel").build();
    }

    public static FipeValorResponse makeFipeValorResponse(){
        return  FipeValorResponse.builder().valor("R$ 96.382,00").tipoVeiculo(1).marca("VW - VolksWagen")
                .modelo("AMAROK High.CD 2.0 16V TDI 4x4 Dies. Aut")
                .anoModelo("2014")
                .combustivel("Diesel")
                .codigofipe("005340-6")
                .mesReferencia("abril de 2025")
                .siglaCombustivel("D")
                .build();
    }

    public static Map<String, Object> parseFipeValorResponse(FipeValorResponse fipeValorResponse){
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(fipeValorResponse,
                new TypeReference<Map<String, Object>>() {});
    }

    public static VehicleEventDto makeVehicleEventDto(){
        User user = User.builder().id(UUID.randomUUID()).name("any_name").email("any_mail@com").cpf("508.296.110-10").build();
        return new VehicleEventDto(
                UUID.randomUUID(), "HAK-1281", BigDecimal.valueOf(30.000), 2014, user.getId(),
                Long.parseLong(String.valueOf(1))
                ,Long.parseLong(String.valueOf(1)));
    }

}
