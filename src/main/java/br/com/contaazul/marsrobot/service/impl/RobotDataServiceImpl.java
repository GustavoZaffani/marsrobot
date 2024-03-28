package br.com.contaazul.marsrobot.service.impl;

import br.com.contaazul.marsrobot.dto.RetriveHistoryRobotResponseDTO;
import br.com.contaazul.marsrobot.dto.RobotDataResponseDTO;
import br.com.contaazul.marsrobot.model.Robot;
import br.com.contaazul.marsrobot.model.RobotCommandHistory;
import br.com.contaazul.marsrobot.repository.RobotCommandHistoryRepository;
import br.com.contaazul.marsrobot.repository.RobotRepository;
import br.com.contaazul.marsrobot.service.RobotDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RobotDataServiceImpl implements RobotDataService {

    @Autowired
    private RobotRepository robotRepository;

    @Autowired
    private RobotCommandHistoryRepository robotCommandHistoryRepository;

    @Override
    public RobotDataResponseDTO retrieve(UUID robotId) {
        Robot robot = robotRepository.findByIdOrThrow(robotId);
        return new RobotDataResponseDTO(robot);
    }

    @Override
    public Page<RobotDataResponseDTO> list(Pageable pageable) {
        Page<Robot> robotsPage = robotRepository.findAll(pageable);

        return robotsPage.map(RobotDataResponseDTO::new);
    }

    @Override
    public RetriveHistoryRobotResponseDTO retrieveHistoryRobot(UUID robotId) {
        Robot robot = robotRepository.findByIdOrThrow(robotId);

        List<RobotCommandHistory> history = robotCommandHistoryRepository.findAllByRobotIdOrderBySequenceDesc(robotId);

        return new RetriveHistoryRobotResponseDTO(robot, history);
    }
}
