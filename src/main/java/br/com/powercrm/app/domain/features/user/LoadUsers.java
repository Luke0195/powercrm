package br.com.powercrm.app.domain.features.user;

import br.com.powercrm.app.dto.response.UserResponseDto;

import java.util.List;

public interface LoadUsers {
    List<UserResponseDto> loadUsers();
}
