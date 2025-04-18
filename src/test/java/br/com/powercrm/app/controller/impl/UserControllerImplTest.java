package br.com.powercrm.app.controller.impl;


import br.com.powercrm.app.domain.entities.User;
import br.com.powercrm.app.domain.enums.UserStatus;
import br.com.powercrm.app.dto.request.UserRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@ActiveProfiles("dev")
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerImplTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private UserRequestDto userRequestDto;


    @DisplayName("POST - handleAddUser should returns 400 if no name is provided")
    @Test
    void handleAddUserShouldReturnsBadRequestWhenNoNameIsProvided() throws Exception{
        UserRequestDto userRequestDto = new UserRequestDto(
                null, "any_mail@mail.com", "any_phone", "111.111.111.11",
                "any_zipcode", "any_address", 30, "any_complement", UserStatus.ACTIVE);
        String jsonBody = objectMapper.writeValueAsString(userRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody));
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @DisplayName("POST - handleAddUser should returns 400 if no email is provided")
    @Test
    void handleAddUserShouldReturnsBadRequestWhenNoEmailIsProvided() throws Exception{
        UserRequestDto userRequestDto = new UserRequestDto("any_name", null, "any_phone", "401.729.130-98",
                "any_zipcode", "any_address", 30, "any_complement", UserStatus.ACTIVE);
        String jsonBody = objectMapper.writeValueAsString(userRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @DisplayName("POST - handleAddUser should returns 400 if an invalid e-mail is provided")
    @Test
    void handleAddUserShouldReturnsBadRequestWhenAnInvalidEmailIsProvided() throws Exception{
        UserRequestDto userRequestDto = new UserRequestDto("any_name", "any_mail", "any_phone",
                "401.729.130-98", "any_code", "any_addres", 30, "any_complement", UserStatus.ACTIVE);
        String jsonBody = objectMapper.writeValueAsString(userRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}