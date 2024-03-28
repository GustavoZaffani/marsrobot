package br.com.contaazul.marsrobot.validator.impl;

import br.com.contaazul.marsrobot.dto.RobotInitiatorRequestDTO;
import br.com.contaazul.marsrobot.repository.RobotRepository;
import br.com.contaazul.marsrobot.validator.RobotColisionValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RobotInitiatorValidatorImplTest {

    @InjectMocks
    private RobotInitiatorValidatorImpl robotInitiatorValidatorImpl;

    @Mock
    private MapSizeValidatorImpl mapSizeValidatorImpl;

    @Mock
    private RobotRepository robotRepository;

    @Mock
    private RobotColisionValidator robotColisionValidator;

    @Test
    @DisplayName("Deve lançar exceção quando houver ultrapassado limite de robô")
    void testValidateWhenThrowsMaxRobots() {
        RobotInitiatorRequestDTO robotInitiatorDTO = new RobotInitiatorRequestDTO(
                "Rover",
                "NORTH",
                5,
                5
        );

        when(robotRepository.countByActiveTrue()).thenReturn(10L);

        assertThrows(RuntimeException.class, () -> robotInitiatorValidatorImpl.validate(robotInitiatorDTO));

        verify(mapSizeValidatorImpl).validateLimitSize(5, 5);
        verify(robotColisionValidator).validateExistsRobotInCoordinate(5, 5);
    }

    @Test
    @DisplayName("Deve validar a criação de um robô")
    void testValidate() {
        RobotInitiatorRequestDTO robotInitiatorDTO = new RobotInitiatorRequestDTO(
                "Rover",
                "NORTH",
                5,
                5
        );

        when(robotRepository.countByActiveTrue()).thenReturn(9L);

        assertDoesNotThrow(() -> robotInitiatorValidatorImpl.validate(robotInitiatorDTO));

        verify(mapSizeValidatorImpl).validateLimitSize(5, 5);
        verify(robotColisionValidator).validateExistsRobotInCoordinate(5, 5);
    }

}
