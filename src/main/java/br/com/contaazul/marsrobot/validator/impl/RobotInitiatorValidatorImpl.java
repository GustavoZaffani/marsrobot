package br.com.contaazul.marsrobot.validator.impl;

import br.com.contaazul.marsrobot.constants.MarsConstants;
import br.com.contaazul.marsrobot.dto.RobotInitiatorRequestDTO;
import br.com.contaazul.marsrobot.repository.RobotRepository;
import br.com.contaazul.marsrobot.validator.MapSizeValidator;
import br.com.contaazul.marsrobot.validator.RobotColisionValidator;
import br.com.contaazul.marsrobot.validator.RobotInitiatorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RobotInitiatorValidatorImpl implements RobotInitiatorValidator {

    @Autowired
    private MapSizeValidator mapSizeValidator;

    @Autowired
    private RobotRepository robotRepository;

    @Autowired
    private RobotColisionValidator robotColisionValidator;

    @Override
    public void validate(RobotInitiatorRequestDTO robotInitiatorDTO) {
        mapSizeValidator.validateLimitSize(robotInitiatorDTO.positionX(), robotInitiatorDTO.positionY());
        robotColisionValidator.validateExistsRobotInCoordinate(robotInitiatorDTO.positionX(), robotInitiatorDTO.positionY());
        validateMaxRobots();
    }

    private void validateMaxRobots() {
        Long robotsActiveSize = robotRepository.countByActiveTrue();

        if (Objects.equals(robotsActiveSize, MarsConstants.MAX_ROBOTS)) {
            throw new RuntimeException("Max robots reached");
        }
    }
}
