package br.com.powercrm.app.controller.impl;

import br.com.powercrm.app.controller.UserController;
import br.com.powercrm.app.dto.request.UserRequestDto;
import br.com.powercrm.app.dto.response.UserResponseDto;
import br.com.powercrm.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;


@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<UserResponseDto> handleAddUser(UserRequestDto userRequestDto) {
        return null;
    }
}
