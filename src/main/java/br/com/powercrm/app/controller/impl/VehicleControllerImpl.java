package br.com.powercrm.app.controller.impl;

import br.com.powercrm.app.controller.VehicleController;
import br.com.powercrm.app.dto.request.VehicleRequestDto;
import br.com.powercrm.app.dto.response.VehicleResponseDto;
import br.com.powercrm.app.service.producer.RabbitVehicleProducerService;
import br.com.powercrm.app.service.VehicleService;
import br.com.powercrm.app.utils.http.HttpHelper;
import br.com.powercrm.app.utils.parser.ParserHelper;
import jakarta.servlet.ServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static br.com.powercrm.app.utils.http.HttpHelper.*;

@RestController
@RequiredArgsConstructor
public class VehicleControllerImpl implements VehicleController {

    private final VehicleService vehicleService;

    @Override
    public ResponseEntity<Map<String,Object>> handleValidateVehicle(VehicleRequestDto vehicleRequestDto) {
         vehicleService.add(vehicleRequestDto);
       Map<String,Object> payload =  new HashMap<>();
       payload.put("message", "Veículo recebido com sucesso. Seu veículo está passando pelos nossos processos de válidação.");
       payload.put("status", "PENDING");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(payload);
    }

    @Override
    public ResponseEntity<Page<VehicleResponseDto>> handleLoadVehicles(int page, int size) {
        List<VehicleResponseDto> vehicles = vehicleService.loadVehicles();
        Page<VehicleResponseDto> vehiclesPaged = ParserHelper.parseListToPage(vehicles, page, size);
        return ok(vehiclesPaged);
    }

    @Override
    public ResponseEntity<Void> handleDeleteVehicle(String id) {
        vehicleService.remove(id);
        return noContent();
    }

    @Override
    public ResponseEntity<VehicleResponseDto> handleUpdate(String id, VehicleRequestDto vehicleRequestDto) {
        VehicleResponseDto vehicleResponseDto = vehicleService.update(id,vehicleRequestDto);
        return ok(vehicleResponseDto);
    }


}
