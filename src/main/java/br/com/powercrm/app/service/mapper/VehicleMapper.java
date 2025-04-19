package br.com.powercrm.app.service.mapper;

import br.com.powercrm.app.domain.entities.User;
import br.com.powercrm.app.domain.entities.Vehicle;
import br.com.powercrm.app.dto.request.VehicleRequestDto;
import br.com.powercrm.app.dto.response.VehicleResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VehicleMapper {
    VehicleMapper INSTANCE = Mappers.getMapper(VehicleMapper.class);


    @Mapping(target = "user.id", source ="user.id")
    public Vehicle mapToEntity(VehicleRequestDto vehicleRequestDto, User user);

    public VehicleResponseDto mapToDto(Vehicle vehicle);

}
