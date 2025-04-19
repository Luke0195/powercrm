package br.com.powercrm.app.service;

import br.com.powercrm.app.domain.entities.User;
import br.com.powercrm.app.domain.features.user.AddUser;
import br.com.powercrm.app.domain.features.user.LoadUsers;
import br.com.powercrm.app.domain.features.user.RemoveUser;
import br.com.powercrm.app.dto.request.UserRequestDto;
import br.com.powercrm.app.dto.response.UserResponseDto;
import br.com.powercrm.app.repository.UserRepository;
import br.com.powercrm.app.service.exceptions.ResourceAlreadyExistsException;

import br.com.powercrm.app.service.exceptions.ResourceNotFoundException;
import br.com.powercrm.app.service.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements AddUser, LoadUsers, RemoveUser {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserResponseDto add(UserRequestDto userRequestDto) {
     validateUniqueFields(userRequestDto);
     User user = UserMapper.INSTANCE.mapToEntity(userRequestDto);
     user = userRepository.save(user);
     return UserMapper.INSTANCE.mapToResponseDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "users")
    public List<UserResponseDto> loadUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper.INSTANCE::mapToResponseDto).toList();
    }

    @Override
    public void remove(String id) {
        UUID userId = UUID.fromString(id);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user_id not found"));
        userRepository.delete(user);
    }


    public void validateUniqueFields(UserRequestDto userRequestDto){
        if(userRepository.existsByEmail(userRequestDto.email())) throw new ResourceAlreadyExistsException("This email is already taken!");
        if(userRepository.existsByCpf(userRequestDto.cpf())) throw new ResourceAlreadyExistsException("This CPF is already exists");
    }
}
