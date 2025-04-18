package br.com.powercrm.app.service;

import br.com.powercrm.app.domain.entities.User;
import br.com.powercrm.app.dto.request.UserRequestDto;
import br.com.powercrm.app.dto.response.UserResponseDto;
import br.com.powercrm.app.factories.UserFactory;
import br.com.powercrm.app.repository.UserRepository;
import br.com.powercrm.app.service.exceptions.EntityAlreadyExistsException;
import br.com.powercrm.app.service.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("dev")
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private UserRequestDto userRequestDto;

    private User user;

    @BeforeEach
    void setup(){
        this.userRequestDto = UserFactory.makeUserRequestDto();
        this.user = UserFactory.makeUser(userRequestDto);
    }

    @DisplayName("Add should throws EntityAlreadyExistsException when email already exists")
    @Test
    void addShouldThrowsEntityAlreadyExistsExceptionWhenEmailAlreadyExists(){
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        Assertions.assertThrows(EntityAlreadyExistsException.class, () -> {
            userService.add(userRequestDto);
        });
    }

    @DisplayName("Add should throws EntityAlreadyExistsException when cpf already exists")
    @Test
    void addShouldThrowsEntityAlreadyExistsExceptionWhenCpfAlreadyExists(){
        Mockito.when(userRepository.findByCpf(Mockito.any())).thenReturn(Optional.of(user));
        Assertions.assertThrows(EntityAlreadyExistsException.class, () -> {
            userService.add(userRequestDto);
        });
    }

    @DisplayName("Add should calls save when valid data is provided")
    @Test
    void addShouldSaveAnUserWhenValidDataIsProvided(){
        Mockito.when(userRepository.findByEmail(userRequestDto.email())).thenReturn(Optional.empty());
        Mockito.when(userRepository.findByCpf(userRequestDto.cpf())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        userService.add(userRequestDto);
        Mockito.verify(userRepository).save(Mockito.any());
    }

    @DisplayName("Add should returns an UserResponseDto when valid data is provided")
    @Test
    void addShouldReturnsAnUserResponseDtoWhenValidDataIsProvided(){
        Mockito.when(userRepository.findByEmail(userRequestDto.email())).thenReturn(Optional.empty());
        Mockito.when(userRepository.findByCpf(userRequestDto.cpf())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserResponseDto userResponseDto = userService.add(userRequestDto);
        Assertions.assertNotNull(userResponseDto.id());
        Assertions.assertEquals("any_name", userResponseDto.name());
        Assertions.assertEquals("any_mail@mail.com", userResponseDto.email());
        Assertions.assertEquals("640.290.140-70", userResponseDto.cpf());
    }

}