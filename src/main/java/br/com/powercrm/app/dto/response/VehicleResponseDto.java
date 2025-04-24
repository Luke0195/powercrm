package br.com.powercrm.app.dto.response;

import br.com.powercrm.app.domain.entities.Brand;
import br.com.powercrm.app.domain.entities.Model;
import br.com.powercrm.app.domain.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

import java.util.UUID;

public record VehicleResponseDto(
        UUID id,
        String plate,
        @JsonProperty("advertised_plate")
        BigDecimal advertisedPlate,
        Integer year,
        User user,
        Brand brand,
        Model model,
        @JsonProperty("fipe_price")
        BigDecimal fipePrice
        ) {
}
