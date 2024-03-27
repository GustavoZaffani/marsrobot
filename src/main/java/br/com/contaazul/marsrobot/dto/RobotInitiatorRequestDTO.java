package br.com.contaazul.marsrobot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RobotInitiatorRequestDTO(

        @NotBlank(message = "Name is mandatory")
        String name,
        @NotBlank(message = "Direction is mandatory")
        @Pattern(regexp = "^(SOUTH|NORTH|WEST|EAST)$", message = "Invalid direction. Use NORTH, SOUTH, WEST or EAST.")
        String direction,
        @NotNull(message = "Position X is mandatory")
        Integer positionX,
        @NotNull(message = "Position Y is mandatory")
        Integer positionY) {
}
