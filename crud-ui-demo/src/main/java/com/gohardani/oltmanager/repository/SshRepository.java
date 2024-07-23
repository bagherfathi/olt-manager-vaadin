package com.gohardani.oltmanager.repository;

import com.gohardani.oltmanager.entity.OltType;
import com.gohardani.oltmanager.entity.SshCommand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SshRepository extends JpaRepository<SshCommand, Long> {
    List<SshCommand> findByNameContainingIgnoreCase(String name);
    List<SshCommand> findSshCommandsByOltType(OltType oltType);
}
