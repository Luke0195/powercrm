package br.com.powercrm.app.service.producer;

import br.com.powercrm.app.domain.entities.Vehicle;
import br.com.powercrm.app.dto.request.VehicleRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RabbitVehicleProducerService {

    private final RabbitTemplate rabbitTemplate;


    public void sendVehicleToValidationQueue(Vehicle vehicle, String exchange){
        rabbitTemplate.convertSendAndReceive(exchange, "vehicle_routing_key", vehicle);
    }
}
