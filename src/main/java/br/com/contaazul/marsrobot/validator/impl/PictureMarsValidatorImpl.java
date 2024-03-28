package br.com.contaazul.marsrobot.validator.impl;

import br.com.contaazul.marsrobot.model.Robot;
import br.com.contaazul.marsrobot.validator.PictureMarsValidator;
import br.com.contaazul.marsrobot.validator.RobotCommonsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PictureMarsValidatorImpl implements PictureMarsValidator {

    @Autowired
    private RobotCommonsValidator robotCommonsValidator;

    @Override
    public void validate(Robot robot) {
        robotCommonsValidator.isRobotActive(robot);
    }
}
