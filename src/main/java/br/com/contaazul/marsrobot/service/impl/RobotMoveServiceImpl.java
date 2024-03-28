package br.com.contaazul.marsrobot.service.impl;

import br.com.contaazul.marsrobot.components.impl.CommandStrategy;
import br.com.contaazul.marsrobot.dto.LocalizationDTO;
import br.com.contaazul.marsrobot.dto.RobotMoveRequestDTO;
import br.com.contaazul.marsrobot.enumeration.Command;
import br.com.contaazul.marsrobot.model.Robot;
import br.com.contaazul.marsrobot.model.RobotCommandHistory;
import br.com.contaazul.marsrobot.repository.RobotCommandHistoryRepository;
import br.com.contaazul.marsrobot.repository.RobotRepository;
import br.com.contaazul.marsrobot.service.RobotMoveService;
import br.com.contaazul.marsrobot.validator.MapSizeValidator;
import br.com.contaazul.marsrobot.validator.RobotColisionValidator;
import br.com.contaazul.marsrobot.validator.RobotMoveValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RobotMoveServiceImpl implements RobotMoveService {

    @Autowired
    private CommandStrategy commandStrategy;

    @Autowired
    private RobotRepository robotRepository;

    @Autowired
    private RobotCommandHistoryRepository robotCommandHistoryRepository;

    @Autowired
    private MapSizeValidator mapSizeValidator;

    @Autowired
    private RobotColisionValidator robotColisionValidator;

    @Autowired
    private RobotMoveValidator robotMoveValidator;

    @Override
    @Transactional
    public LocalizationDTO move(UUID robotId, RobotMoveRequestDTO robotMoveRequestDTO) {
        Robot robot = robotRepository.findByIdOrThrow(robotId);

        robotMoveValidator.validate(robot);

        Long nextSequenceHistory = retrieveNextSequenceHistory();

        LocalizationDTO currentLocalization = createLocalizationDTO(robot);

        List<RobotCommandHistory> historyList = new LinkedList<>();

        String[] commands = robotMoveRequestDTO.commands().split("");
        for (String command : commands) {
            currentLocalization = commandStrategy.getExecutor(Command.fromValue(command)).execute(currentLocalization);
            mapSizeValidator.validateLimitSize(currentLocalization.coordinateX(), currentLocalization.coordinateY());
            robotColisionValidator.validateColisionOnMoveRobot(robot.getId(), currentLocalization.coordinateX(), currentLocalization.coordinateY());

            robot.updatePosition(currentLocalization.coordinateX(),
                    currentLocalization.coordinateY(),
                    currentLocalization.direction());

            historyList.add(new RobotCommandHistory(
                    robot,
                    nextSequenceHistory,
                    Command.fromValue(command),
                    currentLocalization.coordinateX(),
                    currentLocalization.coordinateY(),
                    currentLocalization.direction())
            );

            nextSequenceHistory++;
        }

        robotRepository.save(robot);
        robotCommandHistoryRepository.saveAll(historyList);

        return currentLocalization;
    }

    public LocalizationDTO createLocalizationDTO(Robot robot) {
        return new LocalizationDTO(robot.getCoordinateX(), robot.getCoordinateY(), robot.getDirection());
    }

    public Long retrieveNextSequenceHistory() {
        return Optional.ofNullable(robotCommandHistoryRepository.findMaxSequenceHistory())
                .map(sequence -> sequence + 1L)
                .orElse(1L);
    }
}
