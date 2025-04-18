package br.com.powercrm.app.service;

import br.com.powercrm.app.domain.entities.User;
import br.com.powercrm.app.dto.request.UserRequestDto;
import br.com.powercrm.app.factories.UserFactory;
import br.com.powercrm.app.repository.UserRepository;
import br.com.powercrm.app.service.exceptions.EntityAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
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
        this.user = UserFactory.makeUser(this.userRequestDto);
    }

    @DisplayName("Add should throws EntityAlreadyExistsException when email already exists")
    @Test
    void addShouldThrowsEntityAlreadyExistsExceptionWhenEmailAlreadyExists(){
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        Assertions.assertThrows(EntityAlreadyExistsException.class, () -> {
            User user = UserFactory.makeUser(userRequestDto);
            userService.add(user);
        });
    }

    @DisplayName("Add should throws EntityAlreadyExistsException when cpf already exists")
    @Test
    void addShouldThrowsEntityAlreadyExistsExceptionWhenCpfAlreadyExists(){
        Mockito.when(userRepository.findByCpf(Mockito.any())).thenReturn(Optional.of(user));
        Assertions.assertThrows(EntityAlreadyExistsException.class, () -> {
            User user = UserFactory.makeUser(userRequestDto);
            userService.add(user);
        });
    }



}