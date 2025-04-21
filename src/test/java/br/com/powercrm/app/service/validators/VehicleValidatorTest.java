package br.com.powercrm.app.service.validators;

import br.com.powercrm.app.domain.entities.Brand;
import br.com.powercrm.app.domain.entities.Model;
import br.com.powercrm.app.domain.entities.User;
import br.com.powercrm.app.domain.entities.Vehicle;
import br.com.powercrm.app.dto.request.VehicleRequestDto;
import br.com.powercrm.app.dto.response.VehicleResponseDto;
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
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class VehicleValidatorTest {
    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleValidator vehicleValidator;

    @Mock
    private UserRepository userRepository;

    private VehicleRequestDto vehicleRequestDto;

    private User user;


    @BeforeEach
    void setup(){
        this.vehicleRequestDto = VehicleFactory.makeVehicleRequestDto();
        this.user = UserFactory.makeUser(UserFactory.makeUserRequestDto());
    }

    @DisplayName("Validate should throws ResourceAlreadyExistsException when vehicle plate already exists")
    @Test
    void validateShouldThrowsResourceAlreadyExistsExceptionWhenVehiclePlateAlreadyExists(){
        Mockito.when(vehicleRepository.existsByPlate(vehicleRequestDto.plate())).thenThrow(ResourceAlreadyExistsException.class);
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> {
            vehicleValidator.verifyIfIsValidPlateAndUserExists(vehicleRequestDto);
        });
    }

    @DisplayName("Validate should throws ResourceNotFoundException when user_id not found")
    @Test
    void validateShouldThrowsResourceNotFoundExceptionWhenUserIdNotFound(){
        Mockito.when(vehicleRepository.existsByPlate(vehicleRequestDto.plate())).thenReturn(false);
        Mockito.when(userRepository.findById(vehicleRequestDto.userId())).thenThrow(ResourceNotFoundException.class);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            vehicleValidator.verifyIfIsValidPlateAndUserExists(vehicleRequestDto);
        });
    }

    @DisplayName("Validate should return an User when valid data is provided")
    @Test
    void validateShouldReturnsAnUserWhenValidDataIsProvided(){
        Mockito.when(vehicleRepository.existsByPlate(vehicleRequestDto.plate())).thenReturn(false);
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        User user = vehicleValidator.verifyIfIsValidPlateAndUserExists(this.vehicleRequestDto);
        Assertions.assertNotNull(user);
    }

    @DisplayName("MapToEntity should parse VehicleDto to Entity")
    @Test
    void mapToEntityShouldMapDataCorrectly(){
        Vehicle vehicle = vehicleValidator.mapToEntity(vehicleRequestDto, user);
        Assertions.assertEquals(BigDecimal.valueOf(30.0), vehicle.getAdvertisedPlate());
        Assertions.assertEquals(2015, vehicle.getVehicleYear());
        Assertions.assertEquals(UserFactory.existingId, vehicle.getUser().getId());
    }

    @DisplayName("MapToDto should parse VehicleResponseDto to Entity")
    @Test
    void mapToDtoShouldMapDataCorrectly(){
        VehicleResponseDto vehicleResponseDto = vehicleValidator
                .mapToDto(VehicleFactory.makeVehicle(vehicleRequestDto, UserFactory.makeUser(UserFactory.makeUserRequestDto())));
        Assertions.assertEquals("any_plate", vehicleResponseDto.plate());
        Assertions.assertEquals(2015, vehicleResponseDto.year());
        Assertions.assertEquals(BigDecimal.valueOf(30.0), vehicleResponseDto.advertisedPlate());
    }

    @DisplayName("MapVehicle should parse VehicleRequestDto to vehicle")
    @Test
    void mapVehicleShouldParseVehicleRequestDtoToVehicle(){
        UUID existingId = UUID.randomUUID();
        Vehicle vehicle = new Vehicle();
        vehicle.setUser(new User());
        vehicle.setBrand(new Brand());
        vehicle.setModel(new Model());
        VehicleRequestDto vehicleRequestDto = new VehicleRequestDto("any_plate", BigDecimal.valueOf(30.000),
                2015,existingId, 21L, 31L);
        vehicleValidator.mapVehicleRequestDtoToVehicle(vehicleRequestDto, vehicle);
        Assertions.assertEquals(existingId, vehicle.getUser().getId());
        Assertions.assertEquals(2015, vehicle.getVehicleYear());
        Assertions.assertEquals("any_plate", vehicle.getPlate());
        Assertions.assertEquals(BigDecimal.valueOf(30.000), vehicle.getAdvertisedPlate());
    }

}