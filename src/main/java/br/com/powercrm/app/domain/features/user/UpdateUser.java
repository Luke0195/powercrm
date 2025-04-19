package br.com.powercrm.app.domain.features.user;

import br.com.powercrm.app.dto.request.UserRequestDto;
import br.com.powercrm.app.dto.response.UserResponseDto;

public interface UpdateUser {
    UserResponseDto update(String id, UserRequestDto user);
}
