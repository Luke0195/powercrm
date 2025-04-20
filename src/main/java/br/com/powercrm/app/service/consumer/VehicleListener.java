package br.com.powercrm.app.service.consumer;

import br.com.powercrm.app.dto.request.VehicleRequestDto;
import br.com.powercrm.app.dto.response.UserResponseDto;
import br.com.powercrm.app.dto.response.VehicleResponseDto;
import br.com.powercrm.app.external.fipe.FipeClient;
import br.com.powercrm.app.external.fipe.dtos.FipeAnosResponse;
import br.com.powercrm.app.external.fipe.dtos.FipeValorResponse;
import br.com.powercrm.app.service.FipeService;
import br.com.powercrm.app.service.VehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import feign.FeignException.TooManyRequests;

import javax.management.Notification;

@Component
@Slf4j
@AllArgsConstructor
public class VehicleListener {


     private final FipeClient fipeClient;
     private final ObjectMapper objectMapper;
     private final VehicleService vehicleService;

    @RabbitListener(queues = {"vehicle_creation_queue"})
    public VehicleResponseDto consumerVehicleQueue(VehicleRequestDto vehicleRequestDto){
      try{

        System.out.println(vehicleRequestDto.brandId());
        System.out.println(vehicleRequestDto.modelId());
        List<FipeAnosResponse> result = fipeClient.getAnos(vehicleRequestDto.brandId().toString(), vehicleRequestDto.modelId().toString());
        if(result.isEmpty()) throw new Exception("Validar a o seu veículo");
        FipeAnosResponse fipeAnosResponse = result.get(0);
        Map<String,Object> payload =  fipeClient.getValor(vehicleRequestDto.brandId().toString(), vehicleRequestDto.modelId().toString(), fipeAnosResponse.codigo());
        BigDecimal vehiclePrice = getValue((String) payload.get("Valor"));
        vehicleRequestDto = new VehicleRequestDto(vehicleRequestDto.plate(), vehicleRequestDto.advertisedPlate(),
                vehicleRequestDto.year(),vehicleRequestDto.userId(),  vehicleRequestDto.brandId(), vehicleRequestDto.modelId(), vehiclePrice);
        //return vehicleService.add(vehicleRequestDto);

      }catch (Exception e){
          e.printStackTrace();
      }
    return null;
    }

    private BigDecimal getValue(String apiPrice) throws ParseException {
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("pt", "BR"));
        Number number = numberFormat.parse(apiPrice.replace("R$", "").trim());
        BigDecimal value = BigDecimal.valueOf(number.doubleValue());
        return value;
    }
}
