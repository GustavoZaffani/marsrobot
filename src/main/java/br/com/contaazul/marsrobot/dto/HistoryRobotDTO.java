package br.com.contaazul.marsrobot.dto;

import br.com.contaazul.marsrobot.enumeration.Command;
import br.com.contaazul.marsrobot.enumeration.Direction;
import br.com.contaazul.marsrobot.model.RobotCommandHistory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record HistoryRobotDTO(
        UUID id,
        Long sequence,
        LocalDateTime commandDate,
        Command command,
        Integer coordinateX,
        Integer coordinateY,
        Direction direction) {

    public HistoryRobotDTO(RobotCommandHistory robotCommandHistory) {
        this(robotCommandHistory.getId(),
                robotCommandHistory.getSequence(),
                robotCommandHistory.getCreatedAt(),
                robotCommandHistory.getCommand(),
                robotCommandHistory.getCoordinateX(),
                robotCommandHistory.getCoordinateY(),
                robotCommandHistory.getDirection());
    }

    public static List<HistoryRobotDTO> fromList(List<RobotCommandHistory> history) {
        return history.stream()
                .map(HistoryRobotDTO::new)
                .toList();
    }
}
