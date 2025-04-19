package br.com.powercrm.app.factories;

import br.com.powercrm.app.domain.entities.User;
import br.com.powercrm.app.domain.entities.Vehicle;
import br.com.powercrm.app.dto.request.VehicleRequestDto;
import br.com.powercrm.app.dto.response.VehicleResponseDto;

import java.math.BigDecimal;
import java.util.UUID;

public class VehicleFactory {

    private VehicleFactory(){}


    public static VehicleRequestDto makeVehicleRequestDto(){
        return new VehicleRequestDto("any_plate", BigDecimal.valueOf(30.000), 2015, UUID.randomUUID());
    }

    public static Vehicle makeVehicle(VehicleRequestDto vehicleRequestDto, User user){
        return Vehicle.builder()
                .plate(vehicleRequestDto.plate())
                .advertisedPlate(vehicleRequestDto.advertisedPlate())
                .user(user)
                .year(vehicleRequestDto.year())
                .build();
    }

    public static VehicleResponseDto makeVehicleResponseDto(Vehicle vehicle){
        return new VehicleResponseDto(vehicle.getId(), vehicle.getPlate(), vehicle.getAdvertisedPlate(),
                vehicle.getYear(), vehicle.getUser(), vehicle.getCreatedAt() );
    }
}
