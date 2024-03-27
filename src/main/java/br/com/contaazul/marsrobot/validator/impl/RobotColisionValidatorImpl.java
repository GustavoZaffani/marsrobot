package br.com.contaazul.marsrobot.validator.impl;

import br.com.contaazul.marsrobot.exception.ColisionException;
import br.com.contaazul.marsrobot.projection.CoordinateProjection;
import br.com.contaazul.marsrobot.repository.RobotRepository;
import br.com.contaazul.marsrobot.validator.RobotColisionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Component
public class RobotColisionValidatorImpl implements RobotColisionValidator {

    @Autowired
    private RobotRepository robotRepository;

    @Override
    public void validateExistsRobotInCoordinate(Integer coordinateX, Integer coordinateY) {
        if (robotRepository.existsByCoordinateXAndCoordinateYAndActiveTrue(coordinateX, coordinateY)) {
            throw new ColisionException("Robot already exists in this position");
        }
    }
    @Override
    public void validateColisionOnMoveRobot(UUID robotId, Integer coordinateX, Integer coordinateY) {
        List<CoordinateProjection> coordinatesOtherRobots = robotRepository.findAllByActiveTrueAndIdNot(robotId);

        Predicate<CoordinateProjection> filterCoordinateColision = coordinate ->
                coordinate.getCoordinateX().equals(coordinateX) && coordinate.getCoordinateY().equals(coordinateY);

        if (coordinatesOtherRobots.stream().anyMatch(filterCoordinateColision)) {
            throw new ColisionException("Another robot already occupies this position");
        }
    }
}
