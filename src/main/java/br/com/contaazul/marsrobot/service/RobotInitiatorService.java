package br.com.contaazul.marsrobot.service;

import br.com.contaazul.marsrobot.dto.RobotInitiatorRequestDTO;
import br.com.contaazul.marsrobot.dto.RobotInitiatorResponseDTO;

public interface RobotInitiatorService {

    RobotInitiatorResponseDTO initRobot(RobotInitiatorRequestDTO robotInitiator);
}
