package br.com.contaazul.marsrobot.components.impl;

import br.com.contaazul.marsrobot.components.CommandExecutor;
import br.com.contaazul.marsrobot.dto.LocalizationDTO;
import br.com.contaazul.marsrobot.enumeration.Command;
import br.com.contaazul.marsrobot.enumeration.Direction;
import org.springframework.stereotype.Component;

@Component
public class CommandTurnRightExecutor implements CommandExecutor {

    @Override
    public LocalizationDTO execute(LocalizationDTO localizationDTO) {
        switch (localizationDTO.direction()) {
            case NORTH -> {
                return localizationDTO.withDirection(Direction.EAST);
            }
            case EAST -> {
                return localizationDTO.withDirection(Direction.SOUTH);
            }
            case SOUTH -> {
                return localizationDTO.withDirection(Direction.WEST);
            }
            case WEST -> {
                return localizationDTO.withDirection(Direction.NORTH);
            }
            default -> throw new IllegalArgumentException("Invalid direction");
        }
    }

    @Override
    public Command getCommand() {
        return Command.TURN_RIGHT;
    }
}
