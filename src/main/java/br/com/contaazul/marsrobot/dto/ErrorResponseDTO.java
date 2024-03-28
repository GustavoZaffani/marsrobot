package br.com.contaazul.marsrobot.dto;

import org.springframework.http.HttpStatus;

import java.util.Date;

public record ErrorResponseDTO(HttpStatus status, String error, Date timestamp) {
    public ErrorResponseDTO(HttpStatus status, String error) {
        this(status, error, new Date());
    }
}
