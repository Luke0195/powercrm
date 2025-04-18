package br.com.powercrm.app.dto.response;

import br.com.powercrm.app.domain.enums.UserStatus;

import java.util.UUID;


public record UserResponseDto(
        UUID id,
        String name,
        String email,
        String phone,
        String cpf,
        String zipCode,
        String address,
        Integer number,
        String complement,
        UserStatus status) {
}
