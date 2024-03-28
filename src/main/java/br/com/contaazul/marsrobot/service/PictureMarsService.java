package br.com.contaazul.marsrobot.service;

import br.com.contaazul.marsrobot.dto.PictureMarsResponseDTO;

import java.util.UUID;

public interface PictureMarsService {

    PictureMarsResponseDTO getPicture(UUID robotId);
}
