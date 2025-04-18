package br.com.powercrm.app.service;

import br.com.powercrm.app.domain.entities.User;
import br.com.powercrm.app.domain.features.user.AddUser;
import br.com.powercrm.app.domain.features.user.LoadUsers;
import br.com.powercrm.app.dto.request.UserRequestDto;
import br.com.powercrm.app.dto.response.UserResponseDto;
import br.com.powercrm.app.repository.UserRepository;
import br.com.powercrm.app.service.exceptions.EntityAlreadyExistsException;

import br.com.powercrm.app.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements AddUser, LoadUsers {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserResponseDto add(UserRequestDto userRequestDto) {
     Optional<User> userAlreadyExists = userRepository.findByEmail(userRequestDto.email());
     if(userAlreadyExists.isPresent()) throw new EntityAlreadyExistsException("This email is already taken!");
     Optional<User> findUserByCpf = userRepository.findByCpf(userRequestDto.cpf());
     if(findUserByCpf.isPresent()) throw new EntityAlreadyExistsException("This cpf is already taken");
     User user = UserMapper.INSTANCE.mapToEntity(userRequestDto);
     user = userRepository.save(user);
     return UserMapper.INSTANCE.mapToResponseDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> loadUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper.INSTANCE::mapToResponseDto).toList();
    }
}
