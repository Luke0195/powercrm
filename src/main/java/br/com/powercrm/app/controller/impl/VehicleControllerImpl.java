package br.com.powercrm.app.controller.impl;

import br.com.powercrm.app.controller.VehicleController;
import br.com.powercrm.app.domain.entities.Vehicle;
import br.com.powercrm.app.dto.request.VehicleRequestDto;
import br.com.powercrm.app.dto.response.VehicleResponseDto;
import br.com.powercrm.app.service.VehicleService;
import br.com.powercrm.app.utils.http.HttpHelper;
import br.com.powercrm.app.utils.parser.ParserHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

import static br.com.powercrm.app.utils.http.HttpHelper.*;

@RestController
@RequiredArgsConstructor
public class VehicleControllerImpl implements VehicleController {

    private final VehicleService vehicleService;
    @Override
    public ResponseEntity<VehicleResponseDto> handleAddVehicle(VehicleRequestDto vehicleRequestDto) {
        VehicleResponseDto vehicleResponseDto = vehicleService.add(vehicleRequestDto);
        URI uri = makeURI(vehicleResponseDto.id());
        return created(uri, vehicleResponseDto);
    }

    @Override
    public ResponseEntity<Page<VehicleResponseDto>> handleLoadVehicles(int page, int size) {
        List<VehicleResponseDto> vehicles = vehicleService.loadVehicles();
        Page<VehicleResponseDto> vehiclesPaged = ParserHelper.parseListToPage(vehicles, page, size);
        return HttpHelper.ok(vehiclesPaged);
    }

    @Override
    public ResponseEntity<Void> handleDeleteVehicle(String id) {
        vehicleService.remove(id);
        return HttpHelper.noContent();
    }

    @Override
    public ResponseEntity<VehicleResponseDto> handleUpdate(String id, VehicleRequestDto vehicleRequestDto) {
        return null;
    }


}
