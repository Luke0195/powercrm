package br.com.powercrm.app.controller.impl;

import br.com.powercrm.app.controller.UserController;
import br.com.powercrm.app.dto.request.UserRequestDto;
import br.com.powercrm.app.dto.response.UserResponseDto;
import br.com.powercrm.app.service.UserService;
import br.com.powercrm.app.utils.parser.ParserHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static br.com.powercrm.app.utils.http.HttpHelper.*;



@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<UserResponseDto> handleAddUser(UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.add(userRequestDto);
        URI uri = makeURI(userResponseDto);
        return created(uri, userResponseDto);
    }

    @Override
    public ResponseEntity<Page<UserResponseDto>> handleLoadUsers(int page, int size, LocalDate start, LocalDate end) {
        List<UserResponseDto> usersLoaded = (Objects.isNull(start) || Objects.isNull(end))
                ? userService.loadUsers() : userService.loadUsersByPeriod(start.toString(), end.toString());
        Page<UserResponseDto> usersPage = ParserHelper.parseListToPage(usersLoaded, page, size);
        return ok(usersPage);
    }


    @Override
    public ResponseEntity<Void> handleDeleteUser(String id) {
        userService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<UserResponseDto> handleUpdateUser(String id, UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.update(id, userRequestDto);
        return ok(userResponseDto);
    }

}
