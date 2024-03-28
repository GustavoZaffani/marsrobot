package br.com.contaazul.marsrobot.validator.impl;


import br.com.contaazul.marsrobot.exception.ColisionException;
import br.com.contaazul.marsrobot.projection.CoordinateProjection;
import br.com.contaazul.marsrobot.repository.RobotRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RobotColisionValidatorImplTest {

    @InjectMocks
    private RobotColisionValidatorImpl robotColisionValidatorImpl;

    @Mock
    private RobotRepository robotRepository;

    @Test
    @DisplayName("Deve lançar exceção ao validar a existência de um robô em uma coordenada")
    void testValidateExistsRobotInCoordinateWhenThrows() {
        Integer coordinateX = 5;
        Integer coordinateY = 5;

        when(robotRepository.existsByCoordinateXAndCoordinateYAndActiveTrue(coordinateX, coordinateY)).thenReturn(Boolean.TRUE);

        assertThrows(ColisionException.class, () -> robotColisionValidatorImpl.validateExistsRobotInCoordinate(coordinateX, coordinateY));
    }

    @Test
    @DisplayName("Deve validar a existência de um robô em uma coordenada")
    void testValidateExistsRobotInCoordinateWhenDoesntThrows() {
        Integer coordinateX = 5;
        Integer coordinateY = 5;

        when(robotRepository.existsByCoordinateXAndCoordinateYAndActiveTrue(coordinateX, coordinateY)).thenReturn(Boolean.FALSE);

        assertDoesNotThrow(() -> robotColisionValidatorImpl.validateExistsRobotInCoordinate(coordinateX, coordinateY));
    }

    @Test
    @DisplayName("Deve lançar exceção ao validar colisão de um robô ao se mover")
    void testValidateColisionWhenThrows() {
        UUID robotId = UUID.randomUUID();
        Integer coordinateX = 5;
        Integer coordinateY = 5;

        CoordinateProjection coordinateRobot1 = mock(CoordinateProjection.class);
        when(coordinateRobot1.getCoordinateX()).thenReturn(5);
        when(coordinateRobot1.getCoordinateY()).thenReturn(5);

        when(robotRepository.findAllByActiveTrueAndIdNot(robotId)).thenReturn(List.of(coordinateRobot1));

        assertThrows(ColisionException.class, () -> robotColisionValidatorImpl.validateColisionOnMoveRobot(robotId, coordinateX, coordinateY));
    }

    @Test
    @DisplayName("Deve validar colisão de um robô ao se mover")
    void testValidateColisionWhenDoesntThrows() {
        UUID robotId = UUID.randomUUID();
        Integer coordinateX = 5;
        Integer coordinateY = 5;

        CoordinateProjection coordinateRobot1 = mock(CoordinateProjection.class);
        when(coordinateRobot1.getCoordinateX()).thenReturn(6);
        when(coordinateRobot1.getCoordinateY()).thenReturn(5);

        when(robotRepository.findAllByActiveTrueAndIdNot(robotId)).thenReturn(List.of(coordinateRobot1));

        assertDoesNotThrow(() -> robotColisionValidatorImpl.validateColisionOnMoveRobot(robotId, coordinateX, coordinateY));
    }
}
