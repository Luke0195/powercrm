package br.com.powercrm.app.service;

import br.com.powercrm.app.domain.entities.User;
import br.com.powercrm.app.dto.request.UserRequestDto;
import br.com.powercrm.app.dto.response.UserResponseDto;
import br.com.powercrm.app.factories.UserFactory;
import br.com.powercrm.app.repository.UserRepository;
import br.com.powercrm.app.service.exceptions.ResourceAlreadyExistsException;
import br.com.powercrm.app.service.exceptions.ResourceNotFoundException;
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

import java.util.Collections;
import java.util.List;

import java.util.Optional;
import java.util.UUID;

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
        Mockito.when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> {
            userService.add(userRequestDto);
        });
    }

    @DisplayName("Add should throws EntityAlreadyExistsException when cpf already exists")
    @Test
    void addShouldThrowsEntityAlreadyExistsExceptionWhenCpfAlreadyExists(){
        Mockito.when(userRepository.existsByCpf(Mockito.any())).thenReturn(true);
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> {
            userService.add(userRequestDto);
        });
    }

    @DisplayName("Add should calls save when valid data is provided")
    @Test
    void addShouldSaveAnUserWhenValidDataIsProvided(){
        Mockito.when(userRepository.existsByEmail(userRequestDto.email())).thenReturn(false);
        Mockito.when(userRepository.existsByCpf(userRequestDto.cpf())).thenReturn(false);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        userService.add(userRequestDto);
        Mockito.verify(userRepository).save(Mockito.any());
    }

    @DisplayName("Add should returns an UserResponseDto when valid data is provided")
    @Test
    void addShouldReturnsAnUserResponseDtoWhenValidDataIsProvided(){
        Mockito.when(userRepository.existsByCpf(Mockito.any())).thenReturn(false);
        Mockito.when(userRepository.existsByCpf(Mockito.any())).thenReturn(false);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserResponseDto userResponseDto = userService.add(userRequestDto);
        Assertions.assertNotNull(userResponseDto.id());
        Assertions.assertEquals("any_name", userResponseDto.name());
        Assertions.assertEquals("any_mail@mail.com", userResponseDto.email());
        Assertions.assertEquals("640.290.140-70", userResponseDto.cpf());
    }

    @DisplayName("LoadUsers should returns an empty list when no data was found")
    @Test
    void loadUsersShouldReturnsAnEmptyListWhenNoDataIsFound(){
        Mockito.when(userRepository.findAll()).thenReturn(Collections.emptyList());
        List<UserResponseDto> userResponseDtoList = userService.loadUsers();
        Assertions.assertEquals(0, userResponseDtoList.size());
    }

    @DisplayName("LoadUsers should returns an list of users when was data")
    @Test
    void loadUsersShouldReturnsAUserListWhenWasData(){
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));
        List<UserResponseDto> userResponseDtoList = userService.loadUsers();
        Assertions.assertEquals(1, userResponseDtoList.size());
    }

    @DisplayName("Delete should throws EntityNotFoundException when invalid id is provided ")
    @Test
    void deleteShouldReturnsEntityNotFoundExceptionWhenInvalidIdIsProvided(){
        String invalidId = UUID.randomUUID().toString();
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
           userService.remove(invalidId);
        });
    }

    @DisplayName("Delete should delete an User when valid id is provided")
    @Test
    void deleteShouldDeleteAnUserWhenValidIdIsProvided(){
        String validId = UUID.randomUUID().toString();
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        userService.remove(validId);
        Mockito.verify(userRepository).delete(user);
    }

}