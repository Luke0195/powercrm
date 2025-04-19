package br.com.powercrm.app.controller;

import br.com.powercrm.app.dto.request.UserRequestDto;
import br.com.powercrm.app.dto.response.UserResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


public interface UserController {

    @RequestMapping(value = "/user")
    ResponseEntity<UserResponseDto> handleAddUser(@Valid @RequestBody UserRequestDto userRequestDto);

    @GetMapping(value = "/users" )
    public ResponseEntity<Page<UserResponseDto>>handleLoadUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size);

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<Void> handleDeleteUser(@PathVariable String id);
}
