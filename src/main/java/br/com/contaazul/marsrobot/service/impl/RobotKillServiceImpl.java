package br.com.contaazul.marsrobot.service.impl;

import br.com.contaazul.marsrobot.repository.RobotRepository;
import br.com.contaazul.marsrobot.service.RobotKillService;
import br.com.contaazul.marsrobot.validator.RobotKillValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RobotKillServiceImpl implements RobotKillService {

    @Autowired
    private RobotKillValidator robotKillValidator;

    @Autowired
    private RobotRepository robotRepository;

    @Override
    @Transactional
    public void killRobot(UUID robotId) {
        var robot = robotRepository.findByIdOrThrow(robotId);

        robotKillValidator.validate(robot);

        robot.kill();

        robotRepository.save(robot);
    }
}
