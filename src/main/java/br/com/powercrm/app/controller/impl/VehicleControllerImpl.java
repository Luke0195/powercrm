package br.com.powercrm.app.controller.impl;

import br.com.powercrm.app.controller.VehicleController;
import br.com.powercrm.app.dto.request.VehicleRequestDto;
import br.com.powercrm.app.dto.response.VehicleResponseDto;
import br.com.powercrm.app.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static br.com.powercrm.app.utils.http.HttpHelper.created;
import static br.com.powercrm.app.utils.http.HttpHelper.makeURI;

@RestController
@RequiredArgsConstructor
public class VehicleControllerImpl implements VehicleController {

    private final VehicleService vehicleService;
    @Override
    public ResponseEntity<VehicleResponseDto> handleAddVehicle(VehicleRequestDto vehicleRequestDto) {
        VehicleResponseDto vehicleResponseDto = vehicleService.add(vehicleRequestDto);
        URI uri = makeURI(vehicleResponseDto.id());
        return created(uri, vehicleResponseDto);
    }

    @Override
    public ResponseEntity<VehicleResponseDto> handleLoadVehicles(int page, int size) {
        return null;
    }
}
