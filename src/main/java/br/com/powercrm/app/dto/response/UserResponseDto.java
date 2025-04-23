package br.com.powercrm.app.dto.response;

import br.com.powercrm.app.domain.entities.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;


import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Dados de resposta do usuário")
public record UserResponseDto(
        @Schema(description = "id",  type = "UUID")
        UUID id,
        @Schema(description = "name",  type = "string")
        String name,
        @Schema(description = "email",  type = "string")
        String email,
        @Schema(description = "phone",  type = "string")
        String phone,
        @Schema(description = "cpf",  type = "string")
        String cpf,
        @Schema(description = "zip_code",  type = "string")
        @JsonProperty("zip_code")
        String zipCode,
        @Schema(description = "address",  type = "string")
        String address,
        @Schema(description = "number",  type = "int")
        Integer number,
        @Schema(description = "complement",  type = "string")
        String complement,
        @Schema(description = "description",  type = "string")
        UserStatus status,
        @JsonProperty("created_at")
        LocalDateTime createdAt) {
}
