package br.com.powercrm.app.service.validators;

import br.com.powercrm.app.domain.entities.User;
import br.com.powercrm.app.dto.request.VehicleRequestDto;
import br.com.powercrm.app.factories.UserFactory;
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

import java.util.Optional;


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

    @DisplayName("Validate should throws ResourceAlreadyExistsException when vehicle plate already exists")
    @Test
    void validateShouldThrowsResourceAlreadyExistsExceptionWhenVehiclePlateAlreadyExists(){
        Mockito.when(vehicleRepository.existsByPlate(vehicleRequestDto.plate())).thenThrow(ResourceAlreadyExistsException.class);
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> {
            vehicleValidator.validate(vehicleRequestDto);
        });
    }

    @DisplayName("Validate should throws ResourceNotFoundException when user_id not found")
    @Test
    void validateShouldThrowsResourceNotFoundExceptionWhenUserIdNotFound(){
        Mockito.when(vehicleRepository.existsByPlate(vehicleRequestDto.plate())).thenReturn(false);
        Mockito.when(userRepository.findById(vehicleRequestDto.userId())).thenThrow(ResourceNotFoundException.class);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            vehicleValidator.validate(vehicleRequestDto);
        });
    }

    @DisplayName("Validate should return an User when valid data is provided")
    @Test
    void validateShouldReturnsAnUserWhenValidDataIsProvided(){
        Mockito.when(vehicleRepository.existsByPlate(vehicleRequestDto.plate())).thenReturn(false);
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(UserFactory.makeUser(UserFactory.makeUserRequestDto())));
        User user = vehicleValidator.validate(this.vehicleRequestDto);
        Assertions.assertNotNull(user);
    }


}