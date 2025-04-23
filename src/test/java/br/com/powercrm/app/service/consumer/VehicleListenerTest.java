package br.com.powercrm.app.service.consumer;


import br.com.powercrm.app.domain.entities.User;
import br.com.powercrm.app.dto.response.VehicleEventDto;
import br.com.powercrm.app.external.fipe.dtos.FipeMarcaResponse;
import br.com.powercrm.app.repository.UserRepository;
import br.com.powercrm.app.repository.VehicleRepository;
import br.com.powercrm.app.service.exceptions.ThirdPartyServiceException;
import br.com.powercrm.app.service.validators.FipeValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;

import java.math.BigDecimal;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class VehicleListenerTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private VehicleRepository vehicleRepository;
    @InjectMocks
    private VehicleListener vehicleListener;
    private VehicleEventDto vehicleEventDto;
    @Mock
    private FipeValidation fipeValidation;
    private User user;

    private UUID existingUserId = UUID.randomUUID();
    private Long existingBrandId = 1L;
    private Long existingModelId = 1L;
    @BeforeEach
    void setup(){
        this.user = User.builder()
                .id(existingUserId)
                .email("any_mail.com")
                .cpf("430.074.800-43")
                .email("lucas@mail.com")
                .build();
    }


    @DisplayName("consumer should throws ThirdPartyException when validation an invalid brand is provided")
    @Test
    void consumerShouldThrowsThirdPartyExceptionWhenValidationAnInvalidParamsIsProvided(){
        Mockito.when(fipeValidation.getMarca(Mockito.any())).thenThrow(ThirdPartyServiceException.class);
        UUID vehicleId = UUID.randomUUID();
        VehicleEventDto vehicleEventDto = new VehicleEventDto(vehicleId,
                "YUP-1239", BigDecimal.valueOf(30.000), 2012, existingUserId,existingBrandId, existingModelId );
        Assertions.assertThrows(ThirdPartyServiceException.class, () -> {
          vehicleListener.consumerVehicleQueue(vehicleEventDto);
        });
    }

    @DisplayName("consumer should throws ThirdPartyException when getModelo throws")
    @Test
    void consumerShouldThrowsThirdPartyExceptionWhenGetModeloThrows(){
        FipeMarcaResponse fipeMarcaResponse = FipeMarcaResponse.builder().codigo("1").nome("marca1").build();
        Mockito.when(fipeValidation.getMarca(Mockito.any())).thenReturn(fipeMarcaResponse);
        Mockito.when(fipeValidation.getModelo(Mockito.anyString(),Mockito.anyLong())).thenThrow(ThirdPartyServiceException.class);
        UUID vehicleId = UUID.randomUUID();
        VehicleEventDto vehicleEventDto = new VehicleEventDto(vehicleId,
                "YUP-1239", BigDecimal.valueOf(30.000), 2012, existingUserId,existingBrandId, existingModelId );
        Assertions.assertThrows(ThirdPartyServiceException.class, () -> {
            vehicleListener.consumerVehicleQueue(vehicleEventDto);
        });
    }

}