package br.com.contaazul.marsrobot.dto;

import br.com.contaazul.marsrobot.enumeration.Direction;
import br.com.contaazul.marsrobot.model.Robot;

import java.time.LocalDateTime;
import java.util.UUID;

public record RobotDataResponseDTO(
        UUID id,
        String name,
        Direction direction,
        Integer coordinateX,
        Integer coordinateY,
        LocalDateTime startOperation,
        Boolean isActive,
        LocalDateTime finishOperation) {

    public RobotDataResponseDTO(Robot robot) {
        this(robot.getId(),
                robot.getName(),
                robot.getDirection(),
                robot.getCoordinateX(),
                robot.getCoordinateY(),
                robot.getStartOperation(),
                robot.getActive(),
                robot.getFinishOperation());
    }
}
