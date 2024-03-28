package br.com.contaazul.marsrobot.service.impl;

import br.com.contaazul.marsrobot.dto.PictureMarsResponseDTO;
import br.com.contaazul.marsrobot.model.Robot;
import br.com.contaazul.marsrobot.repository.RobotRepository;
import br.com.contaazul.marsrobot.service.PictureMarsService;
import br.com.contaazul.marsrobot.validator.PictureMarsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.UUID;

@Service
public class PictureMarsServiceImpl implements PictureMarsService {

    @Autowired
    private RobotRepository robotRepository;

    @Autowired
    private PictureMarsValidator pictureMarsValidator;

    @Override
    public PictureMarsResponseDTO getPicture(UUID robotId) {
        Robot robot = robotRepository.findByIdOrThrow(robotId);
        pictureMarsValidator.validate(robot);

        Path imagePath = Path.of("src/main/resources/static/mars.jpg");

        byte[] imageBytes;

        try {
            imageBytes = Files.readAllBytes(imagePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new PictureMarsResponseDTO(Base64.getEncoder().encodeToString(imageBytes));
    }
}
