package br.com.contaazul.marsrobot.service.impl;

import br.com.contaazul.marsrobot.model.Robot;
import br.com.contaazul.marsrobot.repository.RobotRepository;
import br.com.contaazul.marsrobot.validator.RobotKillValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RobotKillServiceImplTest {

    @InjectMocks
    private RobotKillServiceImpl robotKillServiceImpl;

    @Mock
    private RobotKillValidator robotKillValidator;

    @Mock
    private RobotRepository robotRepository;

    @Captor
    private ArgumentCaptor<Robot> robotCaptor;

    @Test
    @DisplayName("Deve inativar/matar um robô")
    public void testKillRobot() {
        UUID robotId = UUID.randomUUID();

        Robot robot = new Robot();
        robot.setActive(Boolean.TRUE);
        robot.setId(robotId);
        robot.setFinishOperation(null);

        when(robotRepository.findByIdOrThrow(robotId)).thenReturn(robot);

        robotKillServiceImpl.killRobot(robotId);

        verify(robotRepository).save(robotCaptor.capture());

        Robot robotSaved = robotCaptor.getValue();

        assertEquals(Boolean.FALSE, robotSaved.getActive());
        assertNotNull(robotSaved.getFinishOperation());
    }
}
