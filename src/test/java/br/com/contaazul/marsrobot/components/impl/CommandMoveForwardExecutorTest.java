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
class CommandMoveForwardExecutorTest {

    @InjectMocks
    private CommandMoveForwardExecutor commandMoveForwardExecutor;

    @Test
    @DisplayName("Dado uma localização com direção Norte, ao executar o comando de mover para frente, a coordenada Y deve ser incrementada em 1")
    void testExecuteWhenDirectionIsNorth() {
        var localizationDTO = new LocalizationDTO(5, 5, Direction.NORTH);

        assertEquals(new LocalizationDTO(5, 6, Direction.NORTH), commandMoveForwardExecutor.execute(localizationDTO));
    }

    @Test
    @DisplayName("Dado uma localização com direção Sul, ao executar o comando de mover para frente, a coordenada Y deve ser decrementada em 1")
    void testExecuteWhenDirectionIsSouth() {
        var localizationDTO = new LocalizationDTO(5, 5, Direction.SOUTH);

        assertEquals(new LocalizationDTO(5, 4, Direction.SOUTH), commandMoveForwardExecutor.execute(localizationDTO));
    }

    @Test
    @DisplayName("Dado uma localização com direção Lest, ao executar o comando de mover para frente, a coordenada X deve ser incrementado em 1")
    void testExecuteWhenDirectionIsEast() {
        var localizationDTO = new LocalizationDTO(5, 5, Direction.EAST);

        assertEquals(new LocalizationDTO(6, 5, Direction.EAST), commandMoveForwardExecutor.execute(localizationDTO));
    }

    @Test
    @DisplayName("Dado uma localização com direção Oeste, ao executar o comando de mover para frente, a coordenada X deve ser decrementado em 1")
    void testExecuteWhenDirectionIsWest() {
        var localizationDTO = new LocalizationDTO(5, 5, Direction.WEST);

        assertEquals(new LocalizationDTO(4, 5, Direction.WEST), commandMoveForwardExecutor.execute(localizationDTO));
    }

    @Test
    @DisplayName("Deve retornar o comando MOVE_FORWARD")
    void testGetCommand() {
        assertEquals(Command.MOVE_FORWARD, commandMoveForwardExecutor.getCommand());
    }
}
