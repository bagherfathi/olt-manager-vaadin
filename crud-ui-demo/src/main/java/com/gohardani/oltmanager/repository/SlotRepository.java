package com.gohardani.oltmanager.repository;

import com.gohardani.oltmanager.entity.Frame;
import com.gohardani.oltmanager.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SlotRepository extends JpaRepository<Slot, Long> {
    List<Slot> findByBoardNameContainingIgnoreCase(String name);
    List<Slot> findByFrameEquals(Frame frame);
    List<Slot> findByBoardNameContainingIgnoreCaseAndFrameEquals(String name,Frame frame);
    void deleteByFrameEquals(Frame frame);
    Slot findByFrameEqualsAndSlotidEquals(Frame frame,Long slotid);


}
