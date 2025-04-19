package br.com.powercrm.app.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record VehicleRequestDto(
        @NotEmpty(message = "The field plate must be required")
        String plate,
        @NotNull(message = "The field advertised_plate must be required")
        @JsonProperty("advertised_plate")
        BigDecimal advertisedPlate,
        @NotNull(message = "The field year must be required")
        Integer year,
        @NotNull(message = "The field user_id must be required")
        @JsonProperty("user_id")
        UUID userId) {
}
