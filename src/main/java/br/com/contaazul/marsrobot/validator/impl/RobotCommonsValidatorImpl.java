package br.com.contaazul.marsrobot.validator.impl;

import br.com.contaazul.marsrobot.exception.InactiveException;
import br.com.contaazul.marsrobot.model.Robot;
import br.com.contaazul.marsrobot.validator.RobotCommonsValidator;
import org.springframework.stereotype.Component;

@Component
public class RobotCommonsValidatorImpl implements RobotCommonsValidator {

    @Override
    public void isRobotActive(Robot robot) {
        if (Boolean.FALSE.equals(robot.getActive())) {
            throw new InactiveException("Robot is dead");
        }
    }
}
