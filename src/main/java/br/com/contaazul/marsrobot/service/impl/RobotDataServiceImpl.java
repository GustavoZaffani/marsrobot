package br.com.contaazul.marsrobot.service.impl;

import br.com.contaazul.marsrobot.dto.RobotDataResponseDTO;
import br.com.contaazul.marsrobot.model.Robot;
import br.com.contaazul.marsrobot.repository.RobotRepository;
import br.com.contaazul.marsrobot.service.RobotDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RobotDataServiceImpl implements RobotDataService {

    @Autowired
    private RobotRepository robotRepository;

    @Override
    public RobotDataResponseDTO retrieve(UUID robotId) {
        Robot robot = robotRepository.findByIdOrThrow(robotId);
        return new RobotDataResponseDTO(robot);
    }

    @Override
    public List<RobotDataResponseDTO> list() {
        // TODO: paginar

        List<Robot> robots = robotRepository.findAll();

        return robots.stream()
                .map(RobotDataResponseDTO::new)
                .toList();
    }
}
