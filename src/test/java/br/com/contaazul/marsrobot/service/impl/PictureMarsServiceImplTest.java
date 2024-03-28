package br.com.contaazul.marsrobot.service.impl;

import br.com.contaazul.marsrobot.dto.PictureMarsResponseDTO;
import br.com.contaazul.marsrobot.model.Robot;
import br.com.contaazul.marsrobot.repository.RobotRepository;
import br.com.contaazul.marsrobot.validator.PictureMarsValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PictureMarsServiceImplTest {

    @InjectMocks
    private PictureMarsServiceImpl pictureMarsService;

    @Mock
    private RobotRepository robotRepository;

    @Mock
    private PictureMarsValidator pictureMarsValidator;

    @Test
    @DisplayName("Deve retornar a imagem de Marte")
    void testGetPicture() throws IOException {
        UUID robotId = UUID.randomUUID();

        Robot robot = new Robot();
        when(robotRepository.findByIdOrThrow(robotId)).thenReturn(robot);

        byte[] imageBytes = Files.readAllBytes(Path.of("src/main/resources/static/mars.jpg"));
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        PictureMarsResponseDTO response = pictureMarsService.getPicture(robotId);

        assertNotNull(response);
        assertEquals(base64Image, response.base64());

        verify(pictureMarsValidator).validate(robot);
    }
}
