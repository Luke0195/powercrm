package br.com.powercrm.app.service.validators;

import br.com.powercrm.app.dto.request.VehicleRequestDto;
import br.com.powercrm.app.repository.UserRepository;
import br.com.powercrm.app.repository.VehicleRepository;
import br.com.powercrm.app.service.exceptions.ResourceAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VehicleValidator {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public void validate(VehicleRequestDto vehicleRequestDto){
      if(vehicleRepository.existsByPlate(vehicleRequestDto.plate())) throw new ResourceAlreadyExistsException("This plate is already taken!");
    }


}
