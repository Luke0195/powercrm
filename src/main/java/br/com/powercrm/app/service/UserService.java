package br.com.powercrm.app.service;

import br.com.powercrm.app.domain.entities.User;
import br.com.powercrm.app.domain.features.user.*;
import br.com.powercrm.app.dto.request.UserRequestDto;
import br.com.powercrm.app.dto.response.UserResponseDto;
import br.com.powercrm.app.repository.UserRepository;

import br.com.powercrm.app.service.exceptions.ResourceNotFoundException;
import br.com.powercrm.app.service.mapper.UserMapper;
import br.com.powercrm.app.service.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements AddUser, LoadUsers, RemoveUser, UpdateUser, LoadUserByPeriod {

    private final UserRepository userRepository;
    private final UserValidator userValidator;

    @Override
    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public UserResponseDto add(UserRequestDto userRequestDto) {
     userValidator.validateUniqueFields(userRequestDto);
     User user = UserMapper.INSTANCE.mapToEntity(userRequestDto);
     user = userRepository.save(user);
     return UserMapper.INSTANCE.mapToResponseDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("users")
    public List<UserResponseDto> loadUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper.INSTANCE::mapToResponseDto).toList();
    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public void remove(String id) {
        UUID userId = UUID.fromString(id);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user_id not found"));
        userRepository.delete(user);
    }

    @Override
    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public UserResponseDto update(String id, UserRequestDto userRequestDto) {
        User user=  userRepository.findById(UUID.fromString(id)).orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " was not found!"));
        userValidator.mapUserRequestDtoToUser(userRequestDto,user);
        user = userRepository.save(user);
        return UserMapper.INSTANCE.mapToResponseDto(user);
    }

    @Override
    @Transactional
    @Cacheable(value = "users", key = "#start + '-' + #end")
    public List<UserResponseDto> loadUsersByPeriod(String start, String end) {
       List<User> loadUsers = userValidator.findUsersByPeriod(start, end);
       return loadUsers.stream().map(UserMapper.INSTANCE::mapToResponseDto).toList();
    }
}
