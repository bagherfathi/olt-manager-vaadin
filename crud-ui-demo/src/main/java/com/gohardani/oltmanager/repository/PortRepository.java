package com.gohardani.oltmanager.repository;

import com.gohardani.oltmanager.entity.Frame;
import com.gohardani.oltmanager.entity.Port;
import com.gohardani.oltmanager.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PortRepository extends JpaRepository<Port, Long> {
    List<Port> findByFspContainingIgnoreCase(String fsp);
    List<Port> findByFspContainingIgnoreCaseAndSlotEquals(String fsp, Slot slot);
    List<Port> findBySlotEquals(Slot slot);
    @Modifying
    @Transactional
    void deleteBySlotEquals(Slot slot);
}
