package br.com.powercrm.app.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.util.UUID;

public record VehicleRequestDto(
        @NotEmpty(message = "The field plate must be required")
        @Pattern(regexp = "^[A-Z]{3}-\\d{4}$", message = "Invalid Plate. The correct format is AAA-1234.")
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
