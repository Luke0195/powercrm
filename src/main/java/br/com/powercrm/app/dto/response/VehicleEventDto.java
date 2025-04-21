package br.com.powercrm.app.dto.response;

import br.com.powercrm.app.domain.entities.User;
import br.com.powercrm.app.domain.enums.VehicleStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record VehicleEventDto(UUID id,
                              String plate,
                              @JsonProperty("advertised_plate")
                              BigDecimal advertisedPlate,
                              Integer year,
                              UUID userId,
                              Long brandId,
                              Long modelId
                              ) {
}
