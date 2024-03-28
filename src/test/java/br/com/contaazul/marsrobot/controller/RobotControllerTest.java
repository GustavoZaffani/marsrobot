package br.com.contaazul.marsrobot.controller;

import br.com.contaazul.marsrobot.dto.*;
import br.com.contaazul.marsrobot.enumeration.Direction;
import br.com.contaazul.marsrobot.service.RobotDataService;
import br.com.contaazul.marsrobot.service.RobotInitiatorService;
import br.com.contaazul.marsrobot.service.RobotKillService;
import br.com.contaazul.marsrobot.service.RobotMoveService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RobotControllerTest {

    @InjectMocks
    private RobotController robotController;
    @Mock
    private RobotInitiatorService robotInitiatorService;
    @Mock
    private RobotKillService robotKillService;
    @Mock
    private RobotMoveService robotMoveService;
    @Mock
    private RobotDataService robotDataService;

    @Test
    @DisplayName("Deve inicializar o robô")
    void testInitRobot() {
        RobotInitiatorRequestDTO requestDTO = new RobotInitiatorRequestDTO(
                "Rover",
                "NORTH",
                5,
                5
        );

        UriComponentsBuilder uriBuilderMock = mock(UriComponentsBuilder.class);
        when(uriBuilderMock.path(anyString())).thenReturn(uriBuilderMock);
        when(uriBuilderMock.buildAndExpand(any(Object.class))).thenReturn(UriComponentsBuilder.fromUriString("/robot/{id}")
                .buildAndExpand("123")
                .encode());

        RobotInitiatorResponseDTO responseDTO = new RobotInitiatorResponseDTO(
                UUID.randomUUID(),
                requestDTO.name(),
                Direction.NORTH,
                requestDTO.positionX(),
                requestDTO.positionY(),
                null);

        when(robotInitiatorService.initRobot(requestDTO)).thenReturn(responseDTO);

        ResponseEntity<RobotInitiatorResponseDTO> responseEntity = robotController.initRobot(requestDTO, uriBuilderMock);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(responseDTO, responseEntity.getBody());
        verify(robotInitiatorService, times(1)).initRobot(requestDTO);
    }

    @Test
    @DisplayName("Deve retornar os dados do robô")
    void testRetrieveRobotData() {
        UUID robotId = UUID.randomUUID();

        RobotDataResponseDTO responseDTO = new RobotDataResponseDTO(
                robotId,
                "Rover",
                Direction.NORTH,
                5,
                5,
                LocalDateTime.now(),
                Boolean.TRUE,
                null);

        when(robotDataService.retrieve(robotId)).thenReturn(responseDTO);

        ResponseEntity<RobotDataResponseDTO> responseEntity = robotController.retrieveRobotData(robotId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseDTO, responseEntity.getBody());
        verify(robotDataService, times(1)).retrieve(robotId);
    }

    @Test
    @DisplayName("Deve listar os dados dos robôs")
    void testListRobotsData() {
        RobotDataResponseDTO robot1 = new RobotDataResponseDTO(
                UUID.randomUUID(),
                "Rover",
                Direction.NORTH,
                5,
                5,
                LocalDateTime.now(),
                Boolean.TRUE,
                null);

        RobotDataResponseDTO robot2 = new RobotDataResponseDTO(
                UUID.randomUUID(),
                "Curiosity",
                Direction.SOUTH,
                4,
                9,
                LocalDateTime.now(),
                Boolean.TRUE,
                null);

        Page pageResult = mock(Page.class);
        when(pageResult.getContent()).thenReturn(List.of(robot1, robot2));

        when(robotDataService.list(any())).thenReturn(pageResult);

        int page = 0;
        int size = 10;
        String sort = "name";
        String direction = "asc";

        ResponseEntity<Page<RobotDataResponseDTO>> responseEntity = robotController.listRobotsData(page, size, sort, direction);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(robot1, responseEntity.getBody().getContent().get(0));
        assertEquals(robot2, responseEntity.getBody().getContent().get(1));
        verify(robotDataService, times(1)).list(any());
    }

    @Test
    @DisplayName("Deve retornar o histórico do robô")
    void testRetriveHistoryRobot() {
        UUID robotId = UUID.randomUUID();

        RetriveHistoryRobotResponseDTO responseDTO = new RetriveHistoryRobotResponseDTO(
                robotId,
                "Rover",
                Direction.NORTH,
                5,
                5,
                LocalDateTime.now(),
                Boolean.TRUE,
                null,
                List.of());

        when(robotDataService.retrieveHistoryRobot(robotId)).thenReturn(responseDTO);

        ResponseEntity<RetriveHistoryRobotResponseDTO> responseEntity = robotController.retriveHistoryRobot(robotId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseDTO, responseEntity.getBody());
        verify(robotDataService, times(1)).retrieveHistoryRobot(robotId);
    }

    @Test
    @DisplayName("Deve matar o robô")
    void testKillRobot() {
        UUID robotId = UUID.randomUUID();

        ResponseEntity responseEntity = robotController.killRobot(robotId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(robotKillService, times(1)).killRobot(robotId);
    }

    @Test
    @DisplayName("Deve mover o robô")
    void testMoveRobot() {
        UUID robotId = UUID.randomUUID();

        RobotMoveRequestDTO requestDTO = new RobotMoveRequestDTO("MMMM");

        LocalizationDTO localizationDTO = new LocalizationDTO(5, 5, Direction.NORTH);

        when(robotMoveService.move(robotId, requestDTO)).thenReturn(localizationDTO);

        ResponseEntity<RobotMoveResponseDTO> responseEntity = robotController.moveRobot(robotId, requestDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(new RobotMoveResponseDTO(localizationDTO), responseEntity.getBody());
        verify(robotMoveService, times(1)).move(robotId, requestDTO);
    }
}
