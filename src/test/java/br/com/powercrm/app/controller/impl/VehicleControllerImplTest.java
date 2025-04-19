package br.com.powercrm.app.controller.impl;

import br.com.powercrm.app.dto.request.VehicleRequestDto;
import br.com.powercrm.app.factories.VehicleFactory;
import br.com.powercrm.app.service.exceptions.InvalidParamException;
import br.com.powercrm.app.service.exceptions.ResourceAlreadyExistsException;
import br.com.powercrm.app.service.exceptions.ResourceNotFoundException;
import br.com.powercrm.app.service.validators.VehicleValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.validator.cfg.defs.UUIDDef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.xml.transform.Result;
import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class VehicleControllerImplTest {

    private static final String ROUTE_NAME = "/vehicle";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private VehicleRequestDto vehicleRequestDto;

    private UUID existingId = UUID.randomUUID();
    @MockitoBean
    private VehicleValidator vehicleValidator;

    @BeforeEach
    void setup(){
        this.vehicleRequestDto = VehicleFactory.makeVehicleRequestDto();
    }

    @DisplayName("POST - handleAddVehicle should returns 400 if no plate is provided")
    @Test
    void handleAddVehicleShouldReturnsBadRequestWhenNoPlateIsProvided() throws Exception{
        VehicleRequestDto vehicleRequestDto= new VehicleRequestDto(null, BigDecimal.valueOf(30.000), 2010,
                existingId);
        String jsonBody = objectMapper.writeValueAsString(vehicleRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody));
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @DisplayName("POST - handleAddVehicle should returns 400 if advertised_plate is provided")
    @Test
    void handleAddVehicleShouldReturnsBadRequestWhenNoAdvertisedPlateIsProvided() throws Exception{
        VehicleRequestDto vehicleRequestDto = new VehicleRequestDto("any_plate", null, 2010,
                existingId);
        String jsonBody = objectMapper.writeValueAsString(vehicleRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody));
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @DisplayName("POST - handleAddVehicle should returns 400 if no year is provided")
    @Test
    void handleAddVehicleShouldReturnsBadRequestWheNoYearIsProvided() throws Exception{
        VehicleRequestDto vehicleRequestDto = new VehicleRequestDto("any_plate", BigDecimal.valueOf(30.000),
                null, existingId);
        String jsonBody = objectMapper.writeValueAsString(vehicleRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody));
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @DisplayName("POST - handleAddVehicle should returns 400 if no user_id is provided")
    @Test
    void handleAddVehicleShouldReturnsBadRequestWheNoUserIdIsProvided() throws Exception{
        VehicleRequestDto vehicleRequestDto = new VehicleRequestDto("any_plate", BigDecimal.valueOf(30.000),
                2015, null);
        String jsonBody = objectMapper.writeValueAsString(vehicleRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody));
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @DisplayName("POST - handleAddVehicle should returns 422 when vehicle plate already exists")
    @Test
    void handleAddVehicleShouldReturnsUnprocessedEntityWheNoUserPlateIsProvided() throws Exception{
        UUID idExisting = UUID.randomUUID();
        VehicleRequestDto vehicleRequestDto = new VehicleRequestDto("any_plate", BigDecimal.valueOf(30.000),
                2015, idExisting);
        Mockito.when(vehicleValidator.validate(vehicleRequestDto))
                .thenThrow(ResourceAlreadyExistsException.class);
        String jsonBody = objectMapper.writeValueAsString(vehicleRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_NAME)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody));
        resultActions.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @DisplayName("POST - handleAddVehicle should returns 400 when user id not found ")
    @Test
    void handleAddVehicleShouldReturnsUnprocessedEntityWheAnInvalidUserIdIsProvided() throws Exception{
        UUID invalidId = UUID.randomUUID();
        VehicleRequestDto vehicleRequestDto = new VehicleRequestDto("any_plate", BigDecimal.valueOf(30.000),
                2015, invalidId);
        Mockito.when(vehicleValidator.validate(vehicleRequestDto)).thenThrow(ResourceNotFoundException.class);
        String jsonBody = objectMapper.writeValueAsString(vehicleRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/vehicle")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody));
        resultActions.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @DisplayName("POST - handleAddVehicle should returns 201 when user id not found ")
    @Test
    void handleAddVehicleShouldReturnsCreatedOnSuccess() throws Exception{
        UUID validId = UUID.randomUUID();
        VehicleRequestDto vehicleRequestDto = new VehicleRequestDto("any_plate", BigDecimal.valueOf(30.000),
                2015, validId);
        String jsonBody = objectMapper.writeValueAsString(vehicleRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/vehicle")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody));
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @DisplayName("GET - handleLoadVehicles should returns 200 on success ")
    @Test
    void handleLoadVehiclesShouldReturnsSuccessOnSuccess() throws Exception{
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }
}