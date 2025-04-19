package br.com.powercrm.app.service;

import br.com.powercrm.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final UserRepository userRepository;



}
