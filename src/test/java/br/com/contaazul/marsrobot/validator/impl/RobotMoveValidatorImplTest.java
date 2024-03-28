package br.com.contaazul.marsrobot.validator.impl;

import br.com.contaazul.marsrobot.model.Robot;
import br.com.contaazul.marsrobot.validator.RobotCommonsValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RobotMoveValidatorImplTest {

    @InjectMocks
    private RobotMoveValidatorImpl robotMoveValidatorImpl;

    @Mock
    private RobotCommonsValidator robotCommonsValidator;

    @Test
    @DisplayName("Deve validar se o robô está ativo")
    void testValidate() {
        Robot robot = new Robot();

        robotMoveValidatorImpl.validate(robot);

        verify(robotCommonsValidator).isRobotActive(robot);
    }
}
