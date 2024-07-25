package com.gohardani.oltmanager.repository;

import com.gohardani.oltmanager.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SlotRepository extends JpaRepository<Slot, Long> {
    List<Slot> findByBoardNameContainingIgnoreCase(String name);

}
