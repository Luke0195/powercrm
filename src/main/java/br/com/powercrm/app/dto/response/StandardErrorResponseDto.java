package br.com.powercrm.app.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Set;


@Schema(description = "Objeto de resposta http")
public record StandardErrorResponseDto(
        LocalDateTime instant,
        @JsonProperty("status_code")
        Integer statusCode,
        @JsonProperty("path_url")
        String pathUrl,
        String exception,
        String message,
        @JsonProperty("errors_fields")
        Set<FieldErrorResponseDto> errorsField
        ) {
    @Override
    public LocalDateTime instant() {
        return instant;
    }

    @Override
    public Integer statusCode() {
        return statusCode;
    }

    @Override
    public String pathUrl() {
        return pathUrl;
    }

    @Override
    public String exception() {
        return exception;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public Set<FieldErrorResponseDto> errorsField() {
        return errorsField;
    }
}
