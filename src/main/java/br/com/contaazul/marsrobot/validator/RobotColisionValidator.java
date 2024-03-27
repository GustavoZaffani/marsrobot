package br.com.contaazul.marsrobot.validator;

import java.util.UUID;

public interface RobotColisionValidator {

    void validateExistsRobotInCoordinate(Integer coordinateX, Integer coordinateY);

    void validateColisionOnMoveRobot(UUID robotId, Integer coordinateX, Integer coordinateY);
}
