package br.com.contaazul.marsrobot.model;

import br.com.contaazul.marsrobot.enumeration.Direction;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "robot")
@Entity(name = "Robot")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Robot {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "coordinate_x")
    private Integer coordinateX;

    @Column(name = "coordinate_y")
    private Integer coordinateY;

    @Enumerated(EnumType.STRING)
    @Column(name = "direction")
    private Direction direction;

    @Column(name = "start_operation")
    private LocalDateTime startOperation;

    @Column(name = "finish_operation")
    private LocalDateTime finishOperation;

    @Column(name = "active")
    private Boolean active;

    public Robot(String name, Direction direction, Integer coordinateX, Integer coordinateY) {
        this.name = name;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.direction = direction;
        this.active = Boolean.TRUE;
        this.startOperation = LocalDateTime.now();
    }

    public void kill() {
        this.active = Boolean.FALSE;
        this.finishOperation = LocalDateTime.now();
    }

    public void updatePosition(Integer coordinateX, Integer coordinateY, Direction direction) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.direction = direction;
    }
}
