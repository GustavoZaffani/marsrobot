package br.com.contaazul.marsrobot.validator.impl;

import br.com.contaazul.marsrobot.exception.InactiveException;
import br.com.contaazul.marsrobot.model.Robot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class RobotCommonsValidatorImplTest {

    @InjectMocks
    private RobotCommonsValidatorImpl robotCommonsValidatorImpl;

    @Test
    @DisplayName("Deve lançar exceção ao validar se o robô está ativo, para um robô inativo")
    void testIsRobotActiveWhenThrowsException() {
        Robot robot = new Robot();
        robot.setActive(Boolean.FALSE);

        assertThrows(InactiveException.class, () -> robotCommonsValidatorImpl.isRobotActive(robot));
    }

    @Test
    @DisplayName("Deve lançar exceção ao validar se o robô está ativo, para um robô ativo")
    void testIsRobotActiveWhenDoesntThrows() {
        Robot robot = new Robot();
        robot.setActive(Boolean.TRUE);

        assertDoesNotThrow(() -> robotCommonsValidatorImpl.isRobotActive(robot));
    }
}

