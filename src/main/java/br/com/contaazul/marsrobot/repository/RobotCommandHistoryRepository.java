package br.com.contaazul.marsrobot.repository;

import br.com.contaazul.marsrobot.model.RobotCommandHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface RobotCommandHistoryRepository extends JpaRepository<RobotCommandHistory, UUID> {

    @Query("SELECT MAX(history.sequence) FROM RobotCommandHistory history")
    Long findMaxSequenceHistory();
}
