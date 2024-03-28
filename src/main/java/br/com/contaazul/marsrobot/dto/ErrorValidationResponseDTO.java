package br.com.contaazul.marsrobot.dto;

import org.springframework.validation.FieldError;

public record ErrorValidationResponseDTO(String field, String message) {

    public ErrorValidationResponseDTO(FieldError fieldError) {
        this(fieldError.getField(), fieldError.getDefaultMessage());
    }
}
