package br.com.powercrm.app.controller.impl;

import br.com.powercrm.app.dto.request.VehicleRequestDto;
import br.com.powercrm.app.factories.UserFactory;
import br.com.powercrm.app.factories.VehicleFactory;
import br.com.powercrm.app.service.UserService;
import br.com.powercrm.app.service.VehicleService;
import br.com.powercrm.app.service.exceptions.ResourceAlreadyExistsException;
import br.com.powercrm.app.service.exceptions.ResourceNotFoundException;
import br.com.powercrm.app.service.validators.VehicleValidator;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    @Autowired
    private VehicleValidator vehicleValidator;

    @MockitoBean
    private VehicleService vehicleService;
    @Autowired
    private UserService userService;

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
        Mockito.when(vehicleService.add(vehicleRequestDto)).thenThrow(ResourceAlreadyExistsException.class);
        String jsonBody = objectMapper.writeValueAsString(vehicleRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/vehicle")
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
        Mockito.when(vehicleService.add(vehicleRequestDto)).thenThrow(ResourceNotFoundException.class);
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
        Mockito.when(vehicleService.add(vehicleRequestDto)).thenReturn(VehicleFactory.makeVehicleResponseDto(VehicleFactory.makeVehicle(vehicleRequestDto, UserFactory.makeUser(UserFactory.makeUserRequestDto()))));
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

    @DisplayName("DELETE - handleDelete should returns 204 on success")
    @Test
    void handleDeleteShouldReturnsNoContentOnSuccess() throws Exception{
        String validId = UUID.randomUUID().toString();
        Mockito.doNothing().when(vehicleService).remove(validId);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/vehicles/{id}", validId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @DisplayName("DELETE - handleDelete should returns 404 when invalid id is provided")
    @Test
    void handleDeleteShouldReturnsNotFoundWhenInvalidIdIsProvided() throws Exception{
        String invalidId = UUID.randomUUID().toString();
        Mockito.doThrow(ResourceNotFoundException.class).when(vehicleService).remove(invalidId);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/vehicles/{id}", invalidId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @DisplayName("PUT - handleUpdate should returns 404 when invalid id is provided")
    @Test
    void handleUpdateShouldReturnsNotFoundWhenInvalidIdIsProvided() throws Exception{
        String invalidId = UUID.randomUUID().toString();
        Mockito.doThrow(ResourceNotFoundException.class).when(vehicleService).update(invalidId, vehicleRequestDto);
        String jsonBody = objectMapper.writeValueAsString(vehicleRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/vehicles/{id}", invalidId)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @DisplayName("PUT - handleUpdate should returns 200 when valid id is provided")
    @Test
    void handleUpdateShouldReturnsOkWhenValidIdIsProvided() throws Exception{
        String validId = UUID.randomUUID().toString();
        String jsonBody = objectMapper.writeValueAsString(vehicleRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/vehicles/{id}", validId)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }
}