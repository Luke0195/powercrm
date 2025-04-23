package br.com.powercrm.app.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

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
