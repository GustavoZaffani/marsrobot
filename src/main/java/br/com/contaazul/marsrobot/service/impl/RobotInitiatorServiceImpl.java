package br.com.contaazul.marsrobot.service.impl;

import br.com.contaazul.marsrobot.dto.RobotInitiatorRequestDTO;
import br.com.contaazul.marsrobot.dto.RobotInitiatorResponseDTO;
import br.com.contaazul.marsrobot.enumeration.Direction;
import br.com.contaazul.marsrobot.model.Robot;
import br.com.contaazul.marsrobot.repository.RobotRepository;
import br.com.contaazul.marsrobot.service.RobotInitiatorService;
import br.com.contaazul.marsrobot.validator.RobotInitiatorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RobotInitiatorServiceImpl implements RobotInitiatorService {

    @Autowired
    private RobotRepository robotRepository;

    @Autowired
    private RobotInitiatorValidator robotInitiatorValidator;

    @Override
    @Transactional
    public RobotInitiatorResponseDTO initRobot(RobotInitiatorRequestDTO robotInitiator) {
        robotInitiatorValidator.validate(robotInitiator);

        var robot = new Robot(robotInitiator.name(),
                Direction.valueOf(robotInitiator.direction()),
                robotInitiator.positionX(),
                robotInitiator.positionY());

        return new RobotInitiatorResponseDTO(robotRepository.save(robot));
    }
}
