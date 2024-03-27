package br.com.contaazul.marsrobot.dto;

import br.com.contaazul.marsrobot.enumeration.Direction;

public record LocalizationDTO(Integer coordinateX, Integer coordinateY, Direction direction) {

    public LocalizationDTO withCoordinateX(Integer coordinateX) {
        return new LocalizationDTO(coordinateX, this.coordinateY(), this.direction());
    }

    public LocalizationDTO withCoordinateY(Integer coordinateY) {
        return new LocalizationDTO(this.coordinateX(), coordinateY, this.direction());
    }

    public LocalizationDTO withDirection(Direction direction) {
        return new LocalizationDTO(this.coordinateX(), this.coordinateY(), direction);
    }
}
