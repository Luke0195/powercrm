package br.com.powercrm.app.service.validators;

import br.com.powercrm.app.domain.entities.User;
import br.com.powercrm.app.dto.request.UserRequestDto;
import br.com.powercrm.app.factories.UserFactory;
import br.com.powercrm.app.repository.UserRepository;
import br.com.powercrm.app.service.exceptions.InvalidParamException;
import br.com.powercrm.app.service.exceptions.ResourceAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.format.DateTimeParseException;


@ExtendWith(MockitoExtension.class)
class UserValidatorTest {

    @InjectMocks
    private UserValidator userValidator;
    @Mock
    private UserRepository userRepository;

    private UserRequestDto userRequestDto;

    @BeforeEach
    void setup(){
        this.userRequestDto = UserFactory.makeUserRequestDto();
    }
    @DisplayName("ValidateUniqueFields should throws ResourceAlreadyExistsException when existing email is provided")
    @Test
    void validateUniqueFieldsShouldReturnResourceAlreadyExistsWhenAnExistingEmailIsProvided(){
        Mockito.when(userRepository.existsByEmail(Mockito.any())).thenThrow(ResourceAlreadyExistsException.class);
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () ->{
            userValidator.validateUniqueFields(userRequestDto);
        });
    }

    @DisplayName("ValidateUniqueFields should throws ResourceAlreadyExistsException when existing cpf is provided")
    @Test
    void validateUniqueFieldsShouldReturnsResourceAlreadyExistsWhenExistingCpfIsProvided(){
        Mockito.when(userRepository.existsByCpf(Mockito.any())).thenThrow(ResourceAlreadyExistsException.class);
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> {
            userValidator.validateUniqueFields(userRequestDto);
        });
    }

    @DisplayName("ValidateUniqueFields should returns nothing on success")
    @Test
    void validateUniqueFieldsShouldReturnsNothingOnSuccess(){
        userValidator.validateUniqueFields(userRequestDto);
        Mockito.verify(userRepository).existsByEmail(Mockito.any());
        Mockito.verify(userRepository).existsByCpf(Mockito.any());
    }

    @DisplayName("FindUsersByPeriod should throws InvalidParamException when invalid params are provided")
    @Test
    void findUserByPeriodShouldThrowsInvalidParamExceptionWhenInvalidParamsAreProvided(){
        Mockito.when(userValidator.findUsersByPeriod(null, null)).thenThrow(InvalidParamException.class);
        Assertions.assertThrows(InvalidParamException.class, () -> {
                userValidator.findUsersByPeriod(null, null);
        });
    }

    @DisplayName("FindUserByPeriod should throws DateParseException when invalid dates are provided")
    @Test
    void findUserByPeriodShouldThrowsDateParseExceptionWhenValidDatesAreProvided(){
        Assertions.assertThrows(DateTimeParseException.class, () ->{
            userValidator.findUsersByPeriod("any_date", "any_date");
        });
    }
    @DisplayName("MapDate should map data from UserRequestDo to User entity")
    @Test
    void mapDataShouldReturnsAnUserResponseDtoOnSuccess(){
        UserRequestDto userRequestDto = UserFactory.makeUserRequestDto();
        User user = new User();
        userValidator.mapData(userRequestDto, user);
        Assertions.assertEquals("any_name", user.getName());
        Assertions.assertEquals("any_mail@mail.com", user.getEmail());
        Assertions.assertEquals("640.290.140-70", user.getCpf());
    }


}