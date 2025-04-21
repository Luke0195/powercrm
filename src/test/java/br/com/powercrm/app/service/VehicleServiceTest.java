package br.com.powercrm.app.service;

import br.com.powercrm.app.domain.entities.User;
import br.com.powercrm.app.domain.entities.Vehicle;
import br.com.powercrm.app.domain.enums.VehicleStatus;
import br.com.powercrm.app.dto.request.VehicleRequestDto;
import br.com.powercrm.app.dto.response.VehicleEventDto;
import br.com.powercrm.app.dto.response.VehicleResponseDto;
import br.com.powercrm.app.factories.UserFactory;
import br.com.powercrm.app.factories.VehicleFactory;
import br.com.powercrm.app.repository.VehicleRepository;
import br.com.powercrm.app.service.exceptions.ResourceNotFoundException;
import br.com.powercrm.app.service.producer.RabbitVehicleProducerService;
import br.com.powercrm.app.service.validators.VehicleValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private RabbitVehicleProducerService producerService;

    @Mock
    private VehicleValidator vehicleValidator;

    private User user;

    private VehicleRequestDto vehicleRequestDto;
    private Vehicle vehicle;

    @BeforeEach
    void setup(){
        this.user = UserFactory.makeUser(UserFactory.makeUserRequestDto());
        this.vehicleRequestDto = VehicleFactory.makeVehicleRequestDto();
        this.vehicle = VehicleFactory.makeVehicle(vehicleRequestDto, user);
    }

    @DisplayName("Add should calls producerService with correct values")
    @Test
    void addShouldSaveAVehicleWhenValidDataIsProvided(){
        Mockito.when(vehicleValidator.verifyIfIsValidPlateAndUserExists(vehicleRequestDto)).thenReturn(user);
        Mockito.when(vehicleRepository.save(Mockito.any())).thenReturn(vehicle);
        vehicleService.add(vehicleRequestDto);
        VehicleEventDto vehicleEventDto = new VehicleEventDto(UUID.randomUUID(), "HAK-1201", BigDecimal.valueOf(30.000), 2015, UUID.randomUUID(),21L, 10L);
        Mockito.verify(producerService).sendVehicleToValidationQueue(vehicleEventDto, "any_name");

    }

    @DisplayName("LoadVehicles should returns a list on success")
    @Test
    void loadVehiclesShouldReturnsAListOfSuccess(){
        Mockito.when(vehicleRepository.findAll()).thenReturn(List.of(vehicle));
        List<VehicleResponseDto> vehicles = vehicleService.loadVehicles();
        Assertions.assertEquals(1, vehicles.size());
    }

    @DisplayName("RemoveUser should throws ResourceNotFoundException when invalid id is provided ")
    @Test
    void deleteShouldReturnsEntityNotFoundExceptionWhenInvalidIdIsProvided(){
        String invalidId = UUID.randomUUID().toString();
        Mockito.when(vehicleRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            vehicleService.remove(invalidId);
        });
    }

    @DisplayName("UpdateVehicle should update a vehicle when valid data is provided ")
    @Test
    void updateVehicleShouldUpdateUserWhenValidDataIsProvided(){
        String validId = UUID.randomUUID().toString();
        Mockito.when(vehicleValidator.verifyIfIsValidVehicleIdAndUserIdExists(validId, vehicleRequestDto)).thenReturn(vehicle);
        Mockito.when(vehicleRepository.save(Mockito.any())).thenReturn(vehicle);
        VehicleResponseDto vehicleResponseDto = vehicleService.update(validId, vehicleRequestDto);
        Assertions.assertNotNull(vehicleResponseDto);
    }


    @DisplayName("RemoveUser should delete an Vehicle when valid id is provided")
    @Test
    void deleteShouldDeleteAnUserWhenValidIdIsProvided(){
        String validId = UUID.randomUUID().toString();
        Mockito.when(vehicleRepository.findById(Mockito.any())).thenReturn(Optional.of(vehicle));
        vehicleService.remove(validId);
        Mockito.verify(vehicleRepository).delete(vehicle);
    }



}