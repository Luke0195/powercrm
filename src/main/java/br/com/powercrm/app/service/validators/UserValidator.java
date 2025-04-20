package br.com.powercrm.app.service.validators;

import br.com.powercrm.app.domain.entities.User;
import br.com.powercrm.app.dto.request.UserRequestDto;
import br.com.powercrm.app.repository.UserRepository;
import br.com.powercrm.app.service.exceptions.InvalidParamException;
import br.com.powercrm.app.service.exceptions.ResourceAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void validateUniqueFields(UserRequestDto userRequestDto){
        if(userRepository.existsByEmail(userRequestDto.email())) throw new ResourceAlreadyExistsException("This email is already taken!");
        if(userRepository.existsByCpf(userRequestDto.cpf())) throw new ResourceAlreadyExistsException("This CPF is already exists");
    }

    public List<User> findUsersByPeriod(String start, String end)throws DateTimeParseException {
        if(Objects.isNull(start) && Objects.isNull(end)){
            return userRepository.findAll();
        }
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        if(endDate.isBefore(startDate)) throw new InvalidParamException("start_date cannot be after end_date");
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        return userRepository.findAllByCreatedAtBetween(startDateTime, endDateTime);

    }

    public void mapUserRequestDtoToUser(UserRequestDto userRequestDto, User user){
        user.setName(userRequestDto.name());
        user.setEmail(userRequestDto.email());
        user.setPhone(userRequestDto.phone());
        user.setCpf(userRequestDto.cpf());
        user.setZipCode(userRequestDto.zipCode());
        user.setNumber(userRequestDto.number());
        user.setComplement(userRequestDto.complement());
        user.setStatus(userRequestDto.status());
    }


}
