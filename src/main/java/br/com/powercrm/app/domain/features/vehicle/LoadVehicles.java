package br.com.powercrm.app.domain.features.vehicle;

import br.com.powercrm.app.domain.entities.Vehicle;
import br.com.powercrm.app.dto.response.UserResponseDto;
import br.com.powercrm.app.dto.response.VehicleResponseDto;

import java.util.List;

public interface LoadVehicles {
    List<VehicleResponseDto> loadVehicles();
}
