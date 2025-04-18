package br.com.powercrm.app.dto.response;

public record FieldErrorResponseDto(
        String name,
        String description
        ) {
    @Override
    public String name() {
        return name;
    }

    @Override
    public String description() {
        return description;
    }
}
