package br.com.powercrm.app.dto.response;

import br.com.powercrm.app.domain.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;


public record UserResponseDto(
        UUID id,
        String name,
        String email,
        String phone,
        String cpf,
        @JsonProperty("zip_code")
        String zipCode,
        String address,
        Integer number,
        String complement,
        UserStatus status,
        @JsonProperty("created_at")
        LocalDateTime createdAt) {
}
