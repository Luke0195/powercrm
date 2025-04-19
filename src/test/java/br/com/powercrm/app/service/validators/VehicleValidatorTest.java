package br.com.powercrm.app.service.validators;

import br.com.powercrm.app.dto.request.VehicleRequestDto;
import br.com.powercrm.app.factories.VehicleFactory;
import br.com.powercrm.app.repository.UserRepository;
import br.com.powercrm.app.repository.VehicleRepository;
import br.com.powercrm.app.service.exceptions.ResourceAlreadyExistsException;
import br.com.powercrm.app.service.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class VehicleValidatorTest {
    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleValidator vehicleValidator;

    @Mock
    private UserRepository userRepository;

    private VehicleRequestDto vehicleRequestDto;


    @BeforeEach
    void setup(){
        this.vehicleRequestDto = VehicleFactory.makeVehicleRequestDto();
    }

    @DisplayName("Add should throws ResourceAlreadyExistsException when vehicle plate already exists")
    @Test
    void addShouldThrowsResourceAlreadyExistsExceptionWhenVehiclePlateAlreadyExists(){
        Mockito.when(vehicleRepository.existsByPlate(vehicleRequestDto.plate())).thenThrow(ResourceAlreadyExistsException.class);
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> {
            vehicleValidator.validate(vehicleRequestDto);
        });
    }
}