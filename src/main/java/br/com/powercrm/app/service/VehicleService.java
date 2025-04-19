package br.com.powercrm.app.service;

import br.com.powercrm.app.domain.entities.User;
import br.com.powercrm.app.domain.entities.Vehicle;
import br.com.powercrm.app.domain.features.vehicle.AddVehicle;
import br.com.powercrm.app.dto.request.VehicleRequestDto;
import br.com.powercrm.app.dto.response.VehicleResponseDto;
import br.com.powercrm.app.repository.VehicleRepository;
import br.com.powercrm.app.service.mapper.VehicleMapper;
import br.com.powercrm.app.service.validators.VehicleValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VehicleService implements AddVehicle {

    private final VehicleValidator vehicleValidator;
    private final VehicleRepository vehicleRepository;


    @Override
    @Transactional
    public VehicleResponseDto add(VehicleRequestDto vehicleRequestDto) {
       User user =  vehicleValidator.validate(vehicleRequestDto);
       Vehicle vehicle = setValue(vehicleRequestDto, user);
       vehicle = vehicleRepository.save(vehicle);
       return VehicleMapper.INSTANCE.mapToDto(vehicle);
    }

    private Vehicle setValue(VehicleRequestDto vehicleRequestDto, User user){
        return Vehicle.builder()
                .user(user)
                .plate(vehicleRequestDto.plate())
                .advertisedPlate(vehicleRequestDto.advertisedPlate())
                .vehicleYear(vehicleRequestDto.year())
                .build();
    }
}
