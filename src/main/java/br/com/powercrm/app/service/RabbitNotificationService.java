package br.com.powercrm.app.service;

import br.com.powercrm.app.dto.request.VehicleRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RabbitNotificationService {

    private final RabbitTemplate rabbitTemplate;


    public void validateVehicle(VehicleRequestDto vehicleRequestDto, String exchange){
        rabbitTemplate.convertAndSend(exchange, "vehicle_routing_key", vehicleRequestDto);
    }
}
