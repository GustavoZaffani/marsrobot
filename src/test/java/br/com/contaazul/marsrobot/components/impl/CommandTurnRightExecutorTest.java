package br.com.contaazul.marsrobot.components.impl;

import br.com.contaazul.marsrobot.dto.LocalizationDTO;
import br.com.contaazul.marsrobot.enumeration.Command;
import br.com.contaazul.marsrobot.enumeration.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CommandTurnRightExecutorTest {

    @InjectMocks
    private CommandTurnRightExecutor commandTurnRightExecutor;

    @Test
    @DisplayName("Dado uma localização com direção Norte, ao executar o comando de virar à direita, a direção deve ser Leste")
    void testExecuteWhenDirectionIsNorth() {
        var localizationDTO = new LocalizationDTO(5, 5, Direction.NORTH);

        assertEquals(new LocalizationDTO(5, 5, Direction.EAST), commandTurnRightExecutor.execute(localizationDTO));
    }

    @Test
    @DisplayName("Dado uma localização com direção Sul, ao executar o comando de virar à direita, a direção deve ser Oeste")
    void testExecuteWhenDirectionIsSouth() {
        var localizationDTO = new LocalizationDTO(5, 5, Direction.SOUTH);

        assertEquals(new LocalizationDTO(5, 5, Direction.WEST), commandTurnRightExecutor.execute(localizationDTO));
    }

    @Test
    @DisplayName("Dado uma localização com direção Leste, ao executar o comando de virar à direita, a direção deve ser Sul")
    void testExecuteWhenDirectionIsEast() {
        var localizationDTO = new LocalizationDTO(5, 5, Direction.EAST);

        assertEquals(new LocalizationDTO(5, 5, Direction.SOUTH), commandTurnRightExecutor.execute(localizationDTO));
    }

    @Test
    @DisplayName("Dado uma localização com direção Oeste, ao executar o comando de virar à direita, a direção deve ser Norte")
    void testExecuteWhenDirectionIsWest() {
        var localizationDTO = new LocalizationDTO(5, 5, Direction.WEST);

        assertEquals(new LocalizationDTO(5, 5, Direction.NORTH), commandTurnRightExecutor.execute(localizationDTO));
    }

    @Test
    @DisplayName("Deve retornar o comando TURN_RIGHT")
    void testGetCommand() {
        assertEquals(Command.TURN_RIGHT, commandTurnRightExecutor.getCommand());
    }
}
