package br.com.powercrm.app.service;

import br.com.powercrm.app.domain.entities.User;
import br.com.powercrm.app.domain.entities.Vehicle;
import br.com.powercrm.app.domain.features.vehicle.AddVehicle;
import br.com.powercrm.app.domain.features.vehicle.LoadVehicles;
import br.com.powercrm.app.domain.features.vehicle.RemoveVehicle;
import br.com.powercrm.app.domain.features.vehicle.UpdateVehicle;
import br.com.powercrm.app.dto.request.VehicleRequestDto;
import br.com.powercrm.app.dto.response.VehicleEventDto;
import br.com.powercrm.app.dto.response.VehicleResponseDto;
import br.com.powercrm.app.repository.VehicleRepository;
import br.com.powercrm.app.service.exceptions.ResourceNotFoundException;
import br.com.powercrm.app.service.mapper.VehicleMapper;
import br.com.powercrm.app.service.producer.RabbitVehicleProducerService;
import br.com.powercrm.app.service.validators.VehicleValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleService implements AddVehicle, LoadVehicles, RemoveVehicle, UpdateVehicle {

    private final VehicleValidator vehicleValidator;
    private final VehicleRepository vehicleRepository;
    private final RabbitVehicleProducerService rabbitVehicleProducerService;

    @Override
    public void add(VehicleRequestDto vehicleRequestDto) {
       User user =  vehicleValidator.verifyIfIsValidPlateAndUserExists(vehicleRequestDto);
       Vehicle vehicle = setValue(vehicleRequestDto, user);
       VehicleEventDto vehicleEventDto = mapVehicleToVehicleEventDto(vehicle, vehicleRequestDto.brandId(), vehicleRequestDto.modelId());
       rabbitVehicleProducerService.sendVehicleToValidationQueue(vehicleEventDto, "vehicle_exchange");
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleResponseDto> loadVehicles() {
        return vehicleRepository.findAll().stream().map(x -> new VehicleResponseDto(
                x.getId(), x.getPlate(), x.getAdvertisedPlate(), x.getVehicleYear(),
                x.getUser())).toList();
    }

    @Override
    @CacheEvict(value = "vehicles", allEntries = true)
    public void remove(String id) {
        UUID userId = UUID.fromString(id);
        Vehicle vehicle = vehicleRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user_id not found"));
        vehicleRepository.delete(vehicle);
    }

    @Override
    @CacheEvict(value = "vehicles", allEntries = true)
    public VehicleResponseDto update(String id, VehicleRequestDto vehicleRequestDto) {
        Vehicle vehicle = vehicleValidator.verifyIfIsValidVehicleIdAndUserIdExists(id, vehicleRequestDto);
        vehicleValidator.mapVehicleRequestDtoToVehicle(vehicleRequestDto, vehicle);
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

    private static VehicleEventDto mapVehicleToVehicleEventDto(Vehicle vehicle, Long brandId, Long modelId){
     return   new VehicleEventDto(vehicle.getId(), vehicle.getPlate(),
                vehicle.getAdvertisedPlate(), vehicle.getVehicleYear(), vehicle.getUser().getId(),
                brandId, modelId);
    }

}
