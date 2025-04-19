package br.com.powercrm.app.dto.response;

import br.com.powercrm.app.domain.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public record VehicleResponseDto(
        UUID id,
        String plate,
        @JsonProperty("advertised_plate")
        BigDecimal advertisedPlate,
        Integer year,
        User user,
        @JsonProperty("created_at")
        LocalDateTime createdAt) {
}
