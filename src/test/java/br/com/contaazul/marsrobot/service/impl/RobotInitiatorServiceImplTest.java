package br.com.contaazul.marsrobot.service.impl;

import br.com.contaazul.marsrobot.dto.RobotInitiatorRequestDTO;
import br.com.contaazul.marsrobot.model.Robot;
import br.com.contaazul.marsrobot.repository.RobotRepository;
import br.com.contaazul.marsrobot.validator.RobotInitiatorValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RobotInitiatorServiceImplTest {

    @InjectMocks
    private RobotInitiatorServiceImpl robotInitiatorServiceImpl;

    @Mock
    private RobotRepository robotRepository;

    @Mock
    private RobotInitiatorValidator robotInitiatorValidator;

    @Captor
    private ArgumentCaptor<Robot> robotCaptor;

    @Test
    @DisplayName("Deve criar um novo robô")
    void testInitRobot() {
        RobotInitiatorRequestDTO robotInitiatorRequestDTO = new RobotInitiatorRequestDTO(
                "Robot 1",
                "NORTH",
                0,
                0
        );

        when(robotRepository.save(any())).thenReturn(new Robot());

        robotInitiatorServiceImpl.initRobot(robotInitiatorRequestDTO);

        verify(robotRepository).save(robotCaptor.capture());
        verify(robotInitiatorValidator).validate(robotInitiatorRequestDTO);

        Robot robotSaved = robotCaptor.getValue();
        assertEquals("Robot 1", robotSaved.getName());
        assertEquals("NORTH", robotSaved.getDirection().name());
        assertEquals(0, robotSaved.getCoordinateX());
        assertEquals(0, robotSaved.getCoordinateY());
        assertTrue(robotSaved.getActive());
        assertNotNull(robotSaved.getStartOperation());
    }
}
