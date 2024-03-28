package br.com.contaazul.marsrobot.service;

import br.com.contaazul.marsrobot.dto.RetriveHistoryRobotResponseDTO;
import br.com.contaazul.marsrobot.dto.RobotDataResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RobotDataService {

    RobotDataResponseDTO retrieve(UUID robotId);

    Page<RobotDataResponseDTO> list(Pageable pageable);

    RetriveHistoryRobotResponseDTO retrieveHistoryRobot(UUID robotId);
}
