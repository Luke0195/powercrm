package br.com.powercrm.app.service;

import br.com.powercrm.app.domain.entities.User;
import br.com.powercrm.app.dto.request.UserRequestDto;
import br.com.powercrm.app.dto.response.UserResponseDto;
import br.com.powercrm.app.factories.UserFactory;
import br.com.powercrm.app.repository.UserRepository;
import br.com.powercrm.app.service.exceptions.ResourceAlreadyExistsException;
import br.com.powercrm.app.service.exceptions.ResourceNotFoundException;
import br.com.powercrm.app.service.validators.UserValidator;
import org.junit.jupiter.api.*;
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
@TestMethodOrder(MethodOrderer.DisplayName.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserValidator userValidator;
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
        Mockito.doThrow(ResourceAlreadyExistsException.class).when(userValidator).validateUniqueFields(userRequestDto);
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> {
            userService.add(userRequestDto);
        });
    }

    @DisplayName("Add should throws EntityAlreadyExistsException when cpf already exists")
    @Test
    void addShouldThrowsEntityAlreadyExistsExceptionWhenCpfAlreadyExists(){
        Mockito.doThrow(ResourceAlreadyExistsException.class).when(userValidator).validateUniqueFields(userRequestDto);
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> {
            userService.add(userRequestDto);
        });
    }

    @DisplayName("Add should calls save when valid data is provided")
    @Test
    void addShouldSaveAnUserWhenValidDataIsProvided(){
        Mockito.doNothing().when(userValidator).validateUniqueFields(userRequestDto);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        userService.add(userRequestDto);
        Mockito.verify(userRepository).save(Mockito.any());
    }

    @DisplayName("Add should returns an UserResponseDto when valid data is provided")
    @Test
    void addShouldReturnsAnUserResponseDtoWhenValidDataIsProvided(){
        Mockito.doNothing().when(userValidator).validateUniqueFields(userRequestDto);
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

    @DisplayName("RemoveUser should throws ResourceNotFoundException when invalid id is provided ")
    @Test
    void deleteShouldReturnsEntityNotFoundExceptionWhenInvalidIdIsProvided(){
        String invalidId = UUID.randomUUID().toString();
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
           userService.remove(invalidId);
        });
    }

    @DisplayName("RemoveUser should delete an User when valid id is provided")
    @Test
    void deleteShouldDeleteAnUserWhenValidIdIsProvided(){
        String validId = UUID.randomUUID().toString();
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        userService.remove(validId);
        Mockito.verify(userRepository).delete(user);
    }

    @DisplayName("UpdateUser should returns ResourceNotFoundException when invalid id is provided")
    @Test
    void updateUserShouldThrowsResourceNotExceptionWhenInvalidIdIsProvided(){
        String invalidId = UUID.randomUUID().toString();
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            userService.update(invalidId, userRequestDto);
        });
    }

    @DisplayName("UpdateUser should update a user when valid data is provided ")
    @Test
    void updateUserShouldUpdateUserWhenValidDataIsProvided(){
        String validId = UUID.randomUUID().toString();
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserResponseDto userResponseDto = userService.update(validId, userRequestDto);
        Assertions.assertNotNull(userResponseDto);
    }

}