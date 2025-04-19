package br.com.powercrm.app.controller.impl;

import br.com.powercrm.app.controller.VehicleController;
import br.com.powercrm.app.dto.request.VehicleRequestDto;
import br.com.powercrm.app.dto.response.VehicleResponseDto;
import br.com.powercrm.app.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VehicleControllerImpl implements VehicleController {

    private final VehicleService vehicleService;
    @Override
    public ResponseEntity<VehicleResponseDto> handleAddVehicle(VehicleRequestDto vehicleRequestDto) {
        return null;
    }
}
