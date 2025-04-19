package br.com.powercrm.app.service.validators;

import br.com.powercrm.app.dto.request.UserRequestDto;
import br.com.powercrm.app.factories.UserFactory;
import br.com.powercrm.app.repository.UserRepository;
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
}