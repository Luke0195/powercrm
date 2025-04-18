package br.com.powercrm.app.controller;

import br.com.powercrm.app.dto.request.UserRequestDto;
import br.com.powercrm.app.dto.response.UserResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface UserController {

    @RequestMapping(value = "/user")
    ResponseEntity<UserResponseDto> handleAddUser(@Valid @RequestBody UserRequestDto userRequestDto);
}
