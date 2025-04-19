package br.com.powercrm.app.domain.features.user;

import br.com.powercrm.app.dto.response.UserResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface LoadUserByPeriod {

    List<UserResponseDto> loadUsersByPeriod(String start, String end);
}
