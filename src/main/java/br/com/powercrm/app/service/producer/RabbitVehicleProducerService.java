package br.com.powercrm.app.service.producer;

import br.com.powercrm.app.dto.response.VehicleEventDto;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RabbitVehicleProducerService {

    private final RabbitTemplate rabbitTemplate;


    public void sendVehicleToValidationQueue(VehicleEventDto vehicle, String exchange){
        rabbitTemplate.convertSendAndReceive(exchange, "vehicle_routing_key", vehicle);
    }
}
