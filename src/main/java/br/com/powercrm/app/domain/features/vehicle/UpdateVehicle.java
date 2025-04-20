package br.com.powercrm.app.domain.features.vehicle;

import br.com.powercrm.app.dto.request.VehicleRequestDto;
import br.com.powercrm.app.dto.response.VehicleResponseDto;

public interface UpdateVehicle {
    public VehicleResponseDto update(String id, VehicleRequestDto vehicleRequestDto);
}
