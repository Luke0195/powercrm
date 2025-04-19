package br.com.powercrm.app.controller.impl;

import br.com.powercrm.app.dto.request.VehicleRequestDto;
import br.com.powercrm.app.factories.VehicleFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
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

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private VehicleRequestDto vehicleRequestDto;

    @BeforeEach
    void setup(){
        this.vehicleRequestDto = VehicleFactory.makeVehicleRequestDto();
    }

    @DisplayName("POST - handleAddVehicle should returns 400 if no plate is provided")
    @Test
    void handleAddVehicleShouldReturnsBadRequestWhenNoPlateIsProvided() throws Exception{
        VehicleRequestDto vehicleRequestDto= new VehicleRequestDto(null, BigDecimal.valueOf(30.000), 2010, UUID.randomUUID());
        String jsonBody = objectMapper.writeValueAsString(vehicleRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/vehicle")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody));
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}