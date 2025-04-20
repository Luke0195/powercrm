package br.com.powercrm.app.controller;

import br.com.powercrm.app.dto.request.VehicleRequestDto;
import br.com.powercrm.app.dto.response.VehicleResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


public interface VehicleController {

    @PostMapping(value = "/vehicle")
    public ResponseEntity<VehicleResponseDto> handleAddVehicle(@Valid @RequestBody VehicleRequestDto vehicleRequestDto);

    @GetMapping(value = "/vehicles")
    public ResponseEntity<Page<VehicleResponseDto>> handleLoadVehicles(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "10") int size);

    @DeleteMapping(value = "/vehicles/{id}")
    public ResponseEntity<Void> handleDeleteVehicle(@PathVariable String id);

    @PutMapping(value = "/vehicles/{id}")
    public ResponseEntity<VehicleResponseDto> handleUpdate(@PathVariable String id, @RequestBody VehicleRequestDto vehicleRequestDto);
}
