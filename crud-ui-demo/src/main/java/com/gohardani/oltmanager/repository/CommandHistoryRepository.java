package com.gohardani.oltmanager.repository;

import com.gohardani.oltmanager.entity.CommandHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandHistoryRepository extends JpaRepository<CommandHistory, Long> {
//    List<CommandHistory> findBySshCommandIs(SshCommand sshCommand);
//    List<CommandHistory> findByOltIs(Olt olt);
}
