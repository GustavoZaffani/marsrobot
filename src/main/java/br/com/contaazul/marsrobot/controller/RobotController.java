package br.com.contaazul.marsrobot.controller;

import br.com.contaazul.marsrobot.dto.*;
import br.com.contaazul.marsrobot.service.RobotDataService;
import br.com.contaazul.marsrobot.service.RobotInitiatorService;
import br.com.contaazul.marsrobot.service.RobotKillService;
import br.com.contaazul.marsrobot.service.RobotMoveService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RequestMapping("/robot")
@RestController
public class RobotController {

    @Autowired
    private RobotInitiatorService robotInitiatorService;

    @Autowired
    private RobotKillService robotKillService;

    @Autowired
    private RobotMoveService robotMoveService;

    @Autowired
    private RobotDataService retrieveRobotDataService;

    @PostMapping
    public ResponseEntity<RobotInitiatorResponseDTO> initRobot(@Valid @RequestBody RobotInitiatorRequestDTO robotInitiator,
                                                                 UriComponentsBuilder uriBuilder) {

        var robot = robotInitiatorService.initRobot(robotInitiator);
        var uri = uriBuilder.path("/robot/{id}").buildAndExpand(robot.id()).toUri();

        return ResponseEntity.created(uri).body(robot);
    }

    @GetMapping("{id}")
    public ResponseEntity<RobotDataResponseDTO> retrieveRobotData(@PathVariable("id") UUID robotId) {
         return ResponseEntity.ok(retrieveRobotDataService.retrieve(robotId));
    }

    @GetMapping
    public ResponseEntity<List<RobotDataResponseDTO>> listRobotsData() {
        return ResponseEntity.ok(retrieveRobotDataService.list());
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
}
