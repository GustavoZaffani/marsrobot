package br.com.contaazul.marsrobot.dto;

import br.com.contaazul.marsrobot.enumeration.Direction;
import br.com.contaazul.marsrobot.model.Robot;
import br.com.contaazul.marsrobot.model.RobotCommandHistory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record RetriveHistoryRobotResponseDTO(
        UUID id,
        String name,
        Direction direction,
        Integer coordinateX,
        Integer coordinateY,
        LocalDateTime startOperation,
        Boolean isActive,
        LocalDateTime finishOperation,
        List<HistoryRobotDTO> histories) {

    public RetriveHistoryRobotResponseDTO(Robot robot, List<RobotCommandHistory> history) {
        this(robot.getId(),
                robot.getName(),
                robot.getDirection(),
                robot.getCoordinateX(),
                robot.getCoordinateY(),
                robot.getStartOperation(),
                robot.getActive(),
                robot.getFinishOperation(),
                HistoryRobotDTO.fromList(history));
    }
}
