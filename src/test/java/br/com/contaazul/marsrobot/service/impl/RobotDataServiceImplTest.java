package br.com.contaazul.marsrobot.service.impl;

import br.com.contaazul.marsrobot.dto.RetriveHistoryRobotResponseDTO;
import br.com.contaazul.marsrobot.dto.RobotDataResponseDTO;
import br.com.contaazul.marsrobot.enumeration.Command;
import br.com.contaazul.marsrobot.enumeration.Direction;
import br.com.contaazul.marsrobot.model.Robot;
import br.com.contaazul.marsrobot.model.RobotCommandHistory;
import br.com.contaazul.marsrobot.repository.RobotCommandHistoryRepository;
import br.com.contaazul.marsrobot.repository.RobotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RobotDataServiceImplTest {

    @InjectMocks
    private RobotDataServiceImpl robotDataService;

    @Mock
    private RobotRepository robotRepository;

    @Mock
    private RobotCommandHistoryRepository robotCommandHistoryRepository;

    private Robot robot;
    private RobotCommandHistory history;

    @BeforeEach
    void setUp() {
        robot = new Robot();
        robot.setId(UUID.randomUUID());
        robot.setName("Robot 1");
        robot.setDirection(Direction.NORTH);
        robot.setCoordinateX(0);
        robot.setCoordinateY(0);
        robot.setActive(true);
        robot.setStartOperation(LocalDateTime.now());
        robot.setFinishOperation(LocalDateTime.now());

        history = new RobotCommandHistory();
        history.setId(UUID.randomUUID());
        history.setRobot(robot);
        history.setCommand(Command.TURN_RIGHT);
        history.setSequence(1L);
        history.setCreatedAt(LocalDateTime.now());
        history.setCoordinateX(0);
        history.setCoordinateY(0);
        history.setDirection(Direction.EAST);
    }

    @Test
    @DisplayName("Deve retornar os dados de um robô")
    void testRetrieve() {
        UUID robotId = UUID.randomUUID();

        when(robotRepository.findByIdOrThrow(robotId)).thenReturn(robot);

        RobotDataResponseDTO responseDTO = robotDataService.retrieve(robotId);

        assertNotNull(responseDTO);
        assertEquals(robot.getId(), responseDTO.id());
        assertEquals(robot.getName(), responseDTO.name());
        assertEquals(robot.getDirection(), responseDTO.direction());
        assertEquals(robot.getCoordinateX(), responseDTO.coordinateX());
        assertEquals(robot.getCoordinateY(), responseDTO.coordinateY());
        assertEquals(robot.getStartOperation(), responseDTO.startOperation());
        assertEquals(robot.getActive(), responseDTO.isActive());
        assertEquals(robot.getFinishOperation(), responseDTO.finishOperation());
    }

    @Test
    @DisplayName("Deve retornar o histórico de um robô")
    void testRetrieveHistoryRobot() {
        UUID robotId = UUID.randomUUID();

        List<RobotCommandHistory> historyList = List.of(history);

        when(robotRepository.findByIdOrThrow(robotId)).thenReturn(robot);
        when(robotCommandHistoryRepository.findAllByRobotIdOrderBySequenceDesc(robotId)).thenReturn(historyList);

        RetriveHistoryRobotResponseDTO responseDTO = robotDataService.retrieveHistoryRobot(robotId);

        assertNotNull(responseDTO);

        assertEquals(robot.getId(), responseDTO.id());
        assertEquals(robot.getName(), responseDTO.name());
        assertEquals(robot.getDirection(), responseDTO.direction());
        assertEquals(robot.getCoordinateX(), responseDTO.coordinateX());
        assertEquals(robot.getCoordinateY(), responseDTO.coordinateY());
        assertEquals(robot.getStartOperation(), responseDTO.startOperation());
        assertEquals(robot.getActive(), responseDTO.isActive());
        assertEquals(robot.getFinishOperation(), responseDTO.finishOperation());

        assertNotNull(responseDTO.histories());
        assertEquals(1, responseDTO.histories().size());
        assertEquals(history.getId(), responseDTO.histories().get(0).id());
        assertEquals(history.getCommand(), responseDTO.histories().get(0).command());
        assertEquals(history.getSequence(), responseDTO.histories().get(0).sequence());
        assertEquals(history.getCreatedAt(), responseDTO.histories().get(0).commandDate());
        assertEquals(history.getCoordinateX(), responseDTO.histories().get(0).coordinateX());
        assertEquals(history.getCoordinateY(), responseDTO.histories().get(0).coordinateY());
        assertEquals(history.getDirection(), responseDTO.histories().get(0).direction());
    }
}
