package br.com.powercrm.app.controller;

import br.com.powercrm.app.dto.request.VehicleRequestDto;
import br.com.powercrm.app.dto.response.VehicleResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



public interface VehicleController {

    @PostMapping(value = "/vehicle")
    public ResponseEntity<VehicleResponseDto> handleAddVehicle(@Valid @RequestBody VehicleRequestDto vehicleRequestDto);
}
