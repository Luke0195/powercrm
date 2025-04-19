package br.com.powercrm.app.service;

import br.com.powercrm.app.dto.request.VehicleRequestDto;
import br.com.powercrm.app.repository.VehicleRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleRepository vehicleRepository;

    private VehicleRequestDto vehicleRequestDto;





}