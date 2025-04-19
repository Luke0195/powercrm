package br.com.powercrm.app.dto.request;

import br.com.powercrm.app.domain.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;

@Schema(description = "Dados de entrada para salvar um usuário")
public record UserRequestDto(
        @Schema(description = "name", example = "Lucas", required = true,  type = "string")
        @NotEmpty(message = "The field name must be required")
        String name,

        @Schema(description = "email", example = "any_mail@mail.com", required = true,  type = "string")
        @NotEmpty(message =  "The field email must be required")
        @Email(message = "Please provided a valid e-mail")
        String email,

        @Schema(description = "phone", example = "311111111", required = false,  type = "string")
        String phone,

        @Schema(description = "cpf", example = "383.015.940-47", required = true,  type = "string")
        @NotEmpty(message = "The field cpf must be required")
        @CPF(message = "Please provided a valid cpf")
        String cpf,

        @Schema(description = "zip_code", example = "123431-303", required = false,  type = "string")
        @JsonProperty("zip_code")
        String zipCode,

        @Schema(description = "zip_code", example = "Rua dos Anjos Bairro Lourdes", required = false,  type = "string")
        String address,

        @Schema(description = "number", example = "50", required = false, type = "int")
        Integer number,
        @Schema(description = "complement", example = "Casa", required = false, type = "string")
        String complement,

        @Schema(description = "status", example = "ACTIVE", required = false, type = "string")
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
