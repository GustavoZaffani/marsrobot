package br.com.contaazul.marsrobot.components.impl;

import br.com.contaazul.marsrobot.components.CommandExecutor;
import br.com.contaazul.marsrobot.dto.LocalizationDTO;
import br.com.contaazul.marsrobot.enumeration.Command;
import org.springframework.stereotype.Component;

@Component
public class CommandMoveForwardExecutor implements CommandExecutor {

    @Override
    public LocalizationDTO execute(LocalizationDTO localizationDTO) {
        switch (localizationDTO.direction()) {
            case NORTH -> {
                return localizationDTO.withCoordinateY(localizationDTO.coordinateY() + 1);
            }
            case EAST -> {
                return localizationDTO.withCoordinateX(localizationDTO.coordinateX() + 1);
            }
            case SOUTH -> {
                return localizationDTO.withCoordinateY(localizationDTO.coordinateY() - 1);
            }
            case WEST -> {
                return localizationDTO.withCoordinateX(localizationDTO.coordinateX() - 1);
            }
            default -> throw new IllegalArgumentException("Invalid direction");
        }
    }

    @Override
    public Command getCommand() {
        return Command.MOVE_FORWARD;
    }
}
