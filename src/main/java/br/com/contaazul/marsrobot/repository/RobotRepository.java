package br.com.contaazul.marsrobot.repository;

import br.com.contaazul.marsrobot.exception.NotFoundException;
import br.com.contaazul.marsrobot.model.Robot;
import br.com.contaazul.marsrobot.projection.CoordinateProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RobotRepository extends JpaRepository<Robot, UUID> {

    Long countByActiveTrue();

    boolean existsByCoordinateXAndCoordinateYAndActiveTrue(Integer coordinateX, Integer coordinateY);

    default Robot findByIdOrThrow(UUID id) {
        return findById(id).orElseThrow(() -> new NotFoundException("Robot not found"));
    }

    List<CoordinateProjection> findAllByActiveTrueAndIdNot(UUID robotId);
}
