package br.com.powercrm.app.service;

import br.com.powercrm.app.domain.entities.User;
import br.com.powercrm.app.domain.features.user.AddUser;
import br.com.powercrm.app.repository.UserRepository;
import br.com.powercrm.app.service.exceptions.EntityAlreadyExistsException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements AddUser {

    private final UserRepository userRepository;


    @Override
    @Transactional
    public User add(User user) {
     Optional<User> userAlreadyExists = userRepository.findByEmail(user.getEmail());
     if(userAlreadyExists.isPresent()) throw new EntityAlreadyExistsException("This email is already taken!");
     Optional<User> findUserByCpf = userRepository.findByCpf(user.getCpf());
     if(findUserByCpf.isPresent()) throw new EntityAlreadyExistsException("This cpf is already taken");
     return null;
    }
}
