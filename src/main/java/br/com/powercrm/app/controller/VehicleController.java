package br.com.powercrm.app.controller;

import br.com.powercrm.app.dto.request.VehicleRequestDto;
import br.com.powercrm.app.dto.response.VehicleResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


public interface VehicleController {

    @PostMapping(value = "/vehicle")
    public ResponseEntity<VehicleResponseDto> handleAddVehicle(@Valid @RequestBody VehicleRequestDto vehicleRequestDto);

    @GetMapping(value = "/vehicles")
    public ResponseEntity<Page<VehicleResponseDto>> handleLoadVehicles(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "10") int size);
}
