package br.com.powercrm.app.controller.impl;

import br.com.powercrm.app.controller.UserController;
import br.com.powercrm.app.dto.request.UserRequestDto;
import br.com.powercrm.app.dto.response.UserResponseDto;
import br.com.powercrm.app.service.UserService;
import br.com.powercrm.app.utils.parser.ParserHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<UserResponseDto> handleAddUser(UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.add(userRequestDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand("/{id}", userResponseDto.id()).toUri();
        return ResponseEntity.created(uri).body(userResponseDto);
    }

    @Override
    public ResponseEntity<Page<UserResponseDto>> handleLoadUsers(int page, int size) {
        List<UserResponseDto> usersLoaded = userService.loadUsers();
        Page<UserResponseDto> usersPage = ParserHelper.parseListToPage(usersLoaded, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(usersPage);
    }
}
