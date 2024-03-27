package br.com.contaazul.marsrobot.model;

import br.com.contaazul.marsrobot.enumeration.Command;
import br.com.contaazul.marsrobot.enumeration.Direction;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "robot_command_history")
@Entity(name = "RobotCommandHistory")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class RobotCommandHistory {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "robot_id")
    private Robot robot;

    @Column(name = "sequence")
    private Long sequence;

    @Enumerated(EnumType.STRING)
    @Column(name = "command")
    private Command command;

    @Column(name = "coordinate_x")
    private Integer coordinateX;

    @Column(name = "coordinate_y")
    private Integer coordinateY;

    @Enumerated(EnumType.STRING)
    @Column(name = "direction")
    private Direction direction;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public RobotCommandHistory(Robot robot, Long sequence, Command command, Integer coordinateX, Integer coordinateY, Direction direction) {
        this.robot = robot;
        this.sequence = sequence;
        this.command = command;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.direction = direction;
        this.createdAt = LocalDateTime.now();
    }
}
