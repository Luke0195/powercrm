package br.com.powercrm.app.service;

import br.com.powercrm.app.domain.entities.User;
import br.com.powercrm.app.domain.entities.Vehicle;
import br.com.powercrm.app.dto.request.VehicleRequestDto;
import br.com.powercrm.app.dto.response.UserResponseDto;
import br.com.powercrm.app.dto.response.VehicleResponseDto;
import br.com.powercrm.app.factories.UserFactory;
import br.com.powercrm.app.factories.VehicleFactory;
import br.com.powercrm.app.repository.VehicleRepository;
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

import java.util.List;


@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleRepository vehicleRepository;

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

    @DisplayName("Add should save a vehicle when valid data is provided")
    @Test
    void addShouldSaveAVehicleWhenValidDataIsProvided(){
        Mockito.when(vehicleValidator.validate(vehicleRequestDto)).thenReturn(user);
        Mockito.when(vehicleRepository.save(Mockito.any())).thenReturn(vehicle);
        VehicleResponseDto vehicleResponseDto = vehicleService.add(vehicleRequestDto);
        Assertions.assertNotNull(vehicleResponseDto.id());
        Assertions.assertNotNull(vehicleResponseDto.year());
        Assertions.assertNotNull(vehicleResponseDto.plate());
        Assertions.assertNotNull(vehicleResponseDto.advertisedPlate());
    }

    @DisplayName("LoadVehicles should returns a list on success")
    @Test
    void loadVehiclesShouldReturnsAListOfSuccess(){
        Mockito.when(vehicleRepository.findAll()).thenReturn(List.of(vehicle));
        List<VehicleResponseDto> vehicles = vehicleService.loadVehicles();
        Assertions.assertEquals(1, vehicles.size());
    }



}