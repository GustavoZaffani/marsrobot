package br.com.contaazul.marsrobot.validator.impl;

import br.com.contaazul.marsrobot.model.Robot;
import br.com.contaazul.marsrobot.validator.RobotCommonsValidator;
import br.com.contaazul.marsrobot.validator.RobotMoveValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RobotMoveValidatorImpl implements RobotMoveValidator {

    @Autowired
    private RobotCommonsValidator robotCommonsValidator;

    @Override
    public void validate(Robot robot) {
        robotCommonsValidator.isRobotActive(robot);
    }
}
