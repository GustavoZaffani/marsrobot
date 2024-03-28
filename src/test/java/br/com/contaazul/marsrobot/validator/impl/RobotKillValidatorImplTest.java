package br.com.contaazul.marsrobot.validator.impl;

import br.com.contaazul.marsrobot.model.Robot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RobotKillValidatorImplTest {

    @InjectMocks
    private RobotKillValidatorImpl robotKillValidatorImpl;

    @Mock
    private RobotCommonsValidatorImpl robotCommonsValidatorImpl;

    @Test
    @DisplayName("Deve validar se o robô está ativo")
    void testValidate() {
        Robot robot = new Robot();

        robotKillValidatorImpl.validate(robot);

        verify(robotCommonsValidatorImpl).isRobotActive(robot);
    }
}
