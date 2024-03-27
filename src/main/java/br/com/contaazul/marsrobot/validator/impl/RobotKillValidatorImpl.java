package br.com.contaazul.marsrobot.validator.impl;

import br.com.contaazul.marsrobot.model.Robot;
import br.com.contaazul.marsrobot.validator.RobotCommonsValidator;
import br.com.contaazul.marsrobot.validator.RobotKillValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RobotKillValidatorImpl implements RobotKillValidator {

    @Autowired
    private RobotCommonsValidator validator;

    @Override
    public void validate(Robot robot) {
        validator.isRobotActive(robot);
    }
}
