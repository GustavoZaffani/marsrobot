package br.com.contaazul.marsrobot.service;

import br.com.contaazul.marsrobot.dto.LocalizationDTO;
import br.com.contaazul.marsrobot.dto.RobotMoveRequestDTO;

import java.util.UUID;

public interface RobotMoveService {

    LocalizationDTO move(UUID robotId, RobotMoveRequestDTO robotMoveRequestDTO);
}
