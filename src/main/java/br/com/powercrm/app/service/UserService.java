package br.com.powercrm.app.service;

import br.com.powercrm.app.domain.entities.User;
import br.com.powercrm.app.domain.features.user.AddUser;
import org.springframework.stereotype.Service;

@Service
public class UserService implements AddUser {


    @Override
    public User add(User user) {
        return null;
    }
}
