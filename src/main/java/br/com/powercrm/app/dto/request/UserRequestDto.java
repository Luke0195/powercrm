package br.com.powercrm.app.dto.request;

import br.com.powercrm.app.domain.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;

public record UserRequestDto(
        @NotEmpty(message = "The field name must be required")
        String name,
        @NotEmpty(message =  "The field email must be required")
        @Email(message = "Please provided a valid e-mail")
        String email,
        String phone,
        @NotEmpty(message = "The field cpf must be required")
        @CPF(message = "Please provided a valid cpf")
        String cpf,
        String zipCode,
        String address,
        Integer number,
        String complement,
        UserStatus status
) {

    @Override
    public String name() {
        return name;
    }

    @Override
    public String email() {
        return email;
    }

    @Override
    public String phone() {
        return phone;
    }

    @Override
    public String cpf() {
        return cpf;
    }

    @Override
    public String zipCode() {
        return zipCode;
    }

    @Override
    public String address() {
        return address;
    }

    @Override
    public Integer number() {
        return number;
    }

    @Override
    public String complement() {
        return complement;
    }

    @Override
    public UserStatus status() {
        return status;
    }
}
