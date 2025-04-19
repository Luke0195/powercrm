package br.com.powercrm.app.controller.impl;

import br.com.powercrm.app.domain.enums.UserStatus;
import br.com.powercrm.app.dto.request.UserRequestDto;
import br.com.powercrm.app.factories.UserFactory;
import br.com.powercrm.app.service.UserService;
import br.com.powercrm.app.service.exceptions.InvalidParamException;
import br.com.powercrm.app.service.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.core.Local;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ActiveProfiles("dev")
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.DisplayName.class)
class UserControllerImplTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private UserRequestDto userRequestDto;

    @MockitoBean
    private UserService userService;


    @BeforeEach
    void setup(){
        this.userRequestDto = UserFactory.makeUserRequestDto();
    }


    @DisplayName("POST - handleAddUser should returns 400 if no name is provided")
    @Test
    void handleAddUserShouldReturnsBadRequestWhenNoNameIsProvided() throws Exception{
        UserRequestDto userRequestDto = new UserRequestDto(
                null, "any_mail@mail.com", "any_phone", "111.111.111.11",
                "XXXXXX-XXX", "any_address", 30, "any_complement", UserStatus.ACTIVE);
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
                "XXXXXX-XXX", "any_address", 30, "any_complement", UserStatus.ACTIVE);
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
                "401.729.130-98", "XXXXXX-XXX", "any_addres", 30, "any_complement", UserStatus.ACTIVE);
        String jsonBody = objectMapper.writeValueAsString(userRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @DisplayName("POST - handleAddUser should returns 400 if no cpf is provided")
    @Test
    void handleAddUserShouldReturnsBadRequestWhenNoCpfIsProvided() throws Exception{
        UserRequestDto userRequestDto = new UserRequestDto(
                "any_name", "any_mail", "any_phone",
                null, "XXXXXX-XXX", "any_addres",
                30, "any_complement", UserStatus.ACTIVE);
        String jsonBody = objectMapper.writeValueAsString(userRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @DisplayName("POST - handleAddUser shouldReturns 400 if an invalid cpf is provided")
    @Test
    void handleAddUserShouldReturnsBadRequestWhenAnInvalidCpfIsProvided() throws Exception{
        UserRequestDto userRequestDto = new UserRequestDto(
                "any_name", "any_mail", "any_phone",
                "111.111.111-11", "XXXXXX-XXX", "any_addres",
                30, "any_complement", UserStatus.ACTIVE);
        String jsonBody = objectMapper.writeValueAsString(userRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @DisplayName("POST - handleAddUser should returns 201 when valid data is provided")
    @Test
    void handleAddUserShouldReturnsCreatedWhenValidDataIsProvided() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(this.userRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @DisplayName("GET - handleUsers should returns 200 on success")
    @Test
    void handleLoadUsersShouldReturnsSuccessOnSuccess() throws Exception{
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }
    @DisplayName("GET - handleUsers should returns 400 if an end date is after start date")
    @Test
    void handleLoadUsersShouldReturnsBadRequestIfEnDateIsAfterStartDate() throws Exception{
        String start = LocalDate.now().toString();
        String endDate = LocalDate.now().minusDays(1).toString();
        Mockito.doThrow(InvalidParamException.class).when(userService).loadUsersByPeriod(start, endDate);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .param("start", start)
                        .param("end", endDate)
                        .contentType(MediaType.APPLICATION_JSON)
                );
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @DisplayName("DELETE - handleDelete should returns 204 on success")
    @Test
    void handleDeleteShouldReturnsNoContentOnSuccess() throws Exception{
        String validId = UUID.randomUUID().toString();
        Mockito.doNothing().when(userService).remove(validId);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", validId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @DisplayName("DELETE - handleDelete should returns 404 when invalid id is provided")
    @Test
    void handleDeleteShouldReturnsNotFoundWhenInvalidIdIsProvided() throws  Exception{
        String invalidId = UUID.randomUUID().toString();

        Mockito.doThrow(new ResourceNotFoundException("user_id not found"))
                .when(userService).remove(invalidId);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", invalidId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(print());
    }

    @DisplayName("PUT - handleUpdate should returns 404 when invalid id is provided")
    @Test
    void handleUpdateShouldReturnsNotFoundWhenInvalidIdIsProvided() throws Exception{
        String invalidId = UUID.randomUUID().toString();
        Mockito.doThrow(ResourceNotFoundException.class).when(userService).update(invalidId, userRequestDto);
        String jsonBody = objectMapper.writeValueAsString(userRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", invalidId)
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
        String jsonBody = objectMapper.writeValueAsString(userRequestDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", validId)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }
}