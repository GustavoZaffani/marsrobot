package br.com.contaazul.marsrobot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RobotMoveRequestDTO(

        @NotBlank(message = "Commands are mandatory")
        @Pattern(regexp = "^[LRM]+$", message = "Movements must contain only the characters L, R, or M")
        String commands) {
}
