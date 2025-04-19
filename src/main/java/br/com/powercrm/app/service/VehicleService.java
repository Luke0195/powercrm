package br.com.powercrm.app.service;

import br.com.powercrm.app.domain.features.vehicle.AddVehicle;
import br.com.powercrm.app.dto.request.VehicleRequestDto;
import br.com.powercrm.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VehicleService implements AddVehicle {

    private final UserRepository userRepository;


    @Override
    @Transactional
    public VehicleRequestDto add(VehicleRequestDto vehicleRequestDto) {
        return null;
    }
}
