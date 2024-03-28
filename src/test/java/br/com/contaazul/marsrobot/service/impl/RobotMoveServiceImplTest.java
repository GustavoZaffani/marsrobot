package br.com.contaazul.marsrobot.service.impl;

import br.com.contaazul.marsrobot.components.CommandExecutor;
import br.com.contaazul.marsrobot.components.impl.CommandStrategy;
import br.com.contaazul.marsrobot.dto.LocalizationDTO;
import br.com.contaazul.marsrobot.dto.RobotMoveRequestDTO;
import br.com.contaazul.marsrobot.enumeration.Command;
import br.com.contaazul.marsrobot.enumeration.Direction;
import br.com.contaazul.marsrobot.model.Robot;
import br.com.contaazul.marsrobot.model.RobotCommandHistory;
import br.com.contaazul.marsrobot.repository.RobotCommandHistoryRepository;
import br.com.contaazul.marsrobot.repository.RobotRepository;
import br.com.contaazul.marsrobot.validator.MapSizeValidator;
import br.com.contaazul.marsrobot.validator.RobotColisionValidator;
import br.com.contaazul.marsrobot.validator.RobotMoveValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RobotMoveServiceImplTest {

    @InjectMocks
    private RobotMoveServiceImpl robotMoveServiceImpl;

    @Mock
    private CommandStrategy commandStrategy;

    @Mock
    private RobotRepository robotRepository;

    @Mock
    private RobotCommandHistoryRepository robotCommandHistoryRepository;

    @Mock
    private MapSizeValidator mapSizeValidator;

    @Mock
    private RobotColisionValidator robotColisionValidator;

    @Mock
    private RobotMoveValidator robotMoveValidator;

    @Captor
    private ArgumentCaptor<Robot> robotArgumentCaptor;

    @Captor
    private ArgumentCaptor<List<RobotCommandHistory>> robotCommandHistoryListCaptor;

    @Test
    @DisplayName("Deve mover o robô para a posição correta")
    void testMove() {
        UUID robotId = UUID.randomUUID();
        RobotMoveRequestDTO robotMoveRequestDTO = new RobotMoveRequestDTO("MRL");

        Robot robot = new Robot();
        robot.setCoordinateX(0);
        robot.setCoordinateY(3);
        robot.setDirection(Direction.NORTH);

        when(robotRepository.findByIdOrThrow(robotId)).thenReturn(robot);
        when(robotCommandHistoryRepository.findMaxSequenceHistory()).thenReturn(5L);

        mockCommands();

        LocalizationDTO finalPosition = robotMoveServiceImpl.move(robotId, robotMoveRequestDTO);

        verify(robotRepository).save(robotArgumentCaptor.capture());
        verify(robotCommandHistoryRepository).saveAll(robotCommandHistoryListCaptor.capture());
        verify(robotMoveValidator).validate(robot);
        verify(mapSizeValidator, times(3)).validateLimitSize(any(), any());
        verify(robotColisionValidator, times(3)).validateColisionOnMoveRobot(any(), any(), any());

        assertEquals(0, finalPosition.coordinateX());
        assertEquals(4, finalPosition.coordinateY());
        assertEquals(Direction.NORTH, finalPosition.direction());

        validateRobotSaved();
        validateHistoriesSaved(robot);
    }

    private void mockCommands() {
        CommandExecutor executorCommandMoveForward = mock(CommandExecutor.class);
        when(commandStrategy.getExecutor(Command.MOVE_FORWARD)).thenReturn(executorCommandMoveForward);
        when(executorCommandMoveForward.execute(any())).thenReturn(new LocalizationDTO(0, 4, Direction.NORTH));

        CommandExecutor executorCommandTurnRight = mock(CommandExecutor.class);
        when(commandStrategy.getExecutor(Command.TURN_RIGHT)).thenReturn(executorCommandTurnRight);
        when(executorCommandTurnRight.execute(any())).thenReturn(new LocalizationDTO(0, 4, Direction.EAST));

        CommandExecutor executorCommandTurnLeft = mock(CommandExecutor.class);
        when(commandStrategy.getExecutor(Command.TURN_LEFT)).thenReturn(executorCommandTurnLeft);
        when(executorCommandTurnLeft.execute(any())).thenReturn(new LocalizationDTO(0, 4, Direction.NORTH));
    }

    private void validateRobotSaved() {
        Robot robotSaved = robotArgumentCaptor.getValue();
        assertEquals(0, robotSaved.getCoordinateX());
        assertEquals(4, robotSaved.getCoordinateY());
        assertEquals(Direction.NORTH, robotSaved.getDirection());
    }

    private void validateHistoriesSaved(Robot robot) {
        List<RobotCommandHistory> historiesSaved = robotCommandHistoryListCaptor.getValue();
        assertEquals(3, historiesSaved.size());

        assertEquals(6L, historiesSaved.get(0).getSequence());
        assertEquals(robot, historiesSaved.get(0).getRobot());
        assertEquals(Command.MOVE_FORWARD, historiesSaved.get(0).getCommand());
        assertEquals(4, historiesSaved.get(0).getCoordinateY());
        assertEquals(0, historiesSaved.get(0).getCoordinateX());
        assertEquals(Direction.NORTH, historiesSaved.get(0).getDirection());

        assertEquals(7L, historiesSaved.get(1).getSequence());
        assertEquals(robot, historiesSaved.get(1).getRobot());
        assertEquals(Command.TURN_RIGHT, historiesSaved.get(1).getCommand());
        assertEquals(4, historiesSaved.get(1).getCoordinateY());
        assertEquals(0, historiesSaved.get(1).getCoordinateX());
        assertEquals(Direction.EAST, historiesSaved.get(1).getDirection());

        assertEquals(8L, historiesSaved.get(2).getSequence());
        assertEquals(robot, historiesSaved.get(2).getRobot());
        assertEquals(Command.TURN_LEFT, historiesSaved.get(2).getCommand());
        assertEquals(4, historiesSaved.get(2).getCoordinateY());
        assertEquals(0, historiesSaved.get(2).getCoordinateX());
        assertEquals(Direction.NORTH, historiesSaved.get(2).getDirection());
    }
}
