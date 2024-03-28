package br.com.contaazul.marsrobot.controller;

import br.com.contaazul.marsrobot.dto.*;
import br.com.contaazul.marsrobot.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RequestMapping("/v1/robot")
@RestController
public class RobotController {

    @Autowired
    private RobotInitiatorService robotInitiatorService;

    @Autowired
    private RobotKillService robotKillService;

    @Autowired
    private RobotMoveService robotMoveService;

    @Autowired
    private RobotDataService robotDataService;

    @Autowired
    private PictureMarsService pictureMarsService;

    @PostMapping
    public ResponseEntity<RobotInitiatorResponseDTO> initRobot(@Valid @RequestBody RobotInitiatorRequestDTO robotInitiator,
                                                               UriComponentsBuilder uriBuilder) {

        var robot = robotInitiatorService.initRobot(robotInitiator);
        var uri = uriBuilder.path("/robot/{id}").buildAndExpand(robot.id()).toUri();

        return ResponseEntity.created(uri).body(robot);
    }

    @GetMapping("{id}")
    public ResponseEntity<RobotDataResponseDTO> retrieveRobotData(@PathVariable("id") UUID robotId) {
        return ResponseEntity.ok(robotDataService.retrieve(robotId));
    }

    @GetMapping
    public ResponseEntity<Page<RobotDataResponseDTO>> listRobotsData(@RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size,
                                                                     @RequestParam(defaultValue = "name") String sort,
                                                                     @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sort));

        return ResponseEntity.ok(robotDataService.list(pageable));
    }

    @GetMapping("history/{id}")
    public ResponseEntity<RetriveHistoryRobotResponseDTO> retriveHistoryRobot(@PathVariable("id") UUID robotId) {
        return ResponseEntity.ok(robotDataService.retrieveHistoryRobot(robotId));
    }

    @PostMapping("kill/{id}")
    public ResponseEntity killRobot(@PathVariable("id") UUID robotId) {
        robotKillService.killRobot(robotId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("move/{id}")
    public ResponseEntity<RobotMoveResponseDTO> moveRobot(@PathVariable("id") UUID robotId,
                                                          @Valid @RequestBody RobotMoveRequestDTO robotMoveRequestDTO) {

        LocalizationDTO localization = robotMoveService.move(robotId, robotMoveRequestDTO);

        return ResponseEntity.ok(new RobotMoveResponseDTO(localization));
    }

    @GetMapping("/picture/{id}")
    public ResponseEntity<PictureMarsResponseDTO> getPictureRobot(@PathVariable("id") UUID robotId) {
        PictureMarsResponseDTO picture = pictureMarsService.getPicture(robotId);

        return ResponseEntity.ok(picture);
    }
}
