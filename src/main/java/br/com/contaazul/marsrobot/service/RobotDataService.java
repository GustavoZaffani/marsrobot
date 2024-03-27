package br.com.contaazul.marsrobot.service;

import br.com.contaazul.marsrobot.dto.RobotDataResponseDTO;

import java.util.List;
import java.util.UUID;

public interface RobotDataService {

    RobotDataResponseDTO retrieve(UUID robotId);

    List<RobotDataResponseDTO> list();
}
