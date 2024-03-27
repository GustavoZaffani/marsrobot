package br.com.contaazul.marsrobot.dto;

import br.com.contaazul.marsrobot.enumeration.Direction;

public record RobotMoveResponseDTO(Integer coordinateX, Integer coordinateY, Direction direction) {

    public RobotMoveResponseDTO(LocalizationDTO localizationDTO) {
        this(localizationDTO.coordinateX(), localizationDTO.coordinateY(), localizationDTO.direction());
    }
}
