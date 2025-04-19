package br.com.powercrm.app.service.validators;

import br.com.powercrm.app.domain.entities.User;
import br.com.powercrm.app.domain.entities.Vehicle;
import br.com.powercrm.app.dto.request.VehicleRequestDto;
import br.com.powercrm.app.dto.response.VehicleResponseDto;
import br.com.powercrm.app.repository.UserRepository;
import br.com.powercrm.app.repository.VehicleRepository;
import br.com.powercrm.app.service.exceptions.ResourceAlreadyExistsException;
import br.com.powercrm.app.service.exceptions.ResourceNotFoundException;
import br.com.powercrm.app.service.mapper.VehicleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VehicleValidator {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public User validate(VehicleRequestDto vehicleRequestDto){
      if(vehicleRepository.existsByPlate(vehicleRequestDto.plate())) throw new ResourceAlreadyExistsException("This plate is already taken!");
      return userRepository.findById(vehicleRequestDto.userId()).orElseThrow(() ->
              new ResourceNotFoundException("User id has not found"));
    }


    public Vehicle mapToEntity(VehicleRequestDto vehicleRequestDto, User user){
        return VehicleMapper.INSTANCE.mapToEntity(vehicleRequestDto, user);
    }

    public VehicleResponseDto mapToDto(Vehicle entity){
        return VehicleMapper.INSTANCE.mapToDto(entity);
    }



}
