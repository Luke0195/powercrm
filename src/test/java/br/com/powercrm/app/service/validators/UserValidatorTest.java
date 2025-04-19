package br.com.powercrm.app.service.validators;

import br.com.powercrm.app.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserValidatorTest {

    @InjectMocks
    private UserValidator userValidator;
    @Mock
    private UserRepository userRepository;

    @DisplayName("ValidateUniqueFields should throws ResourceAlreadyExistsException when existing email is provided")
    void validateUniqueFieldsShouldReturnResourceAlreadyExistsWhenAnExistingEmailIsProvided(){

    }
}