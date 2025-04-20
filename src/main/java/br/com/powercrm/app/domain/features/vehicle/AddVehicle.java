package br.com.powercrm.app.domain.features.vehicle;

import br.com.powercrm.app.dto.request.VehicleRequestDto;
import br.com.powercrm.app.dto.response.VehicleResponseDto;

public interface AddVehicle {

    public void validateVehicle(VehicleRequestDto vehicleRequestDto);
}
