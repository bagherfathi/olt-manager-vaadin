package com.gohardani.oltmanager.service;

import com.gohardani.oltmanager.entity.Slot;
import com.gohardani.oltmanager.repository.SlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlotService {

    private final SlotRepository slotRepository;
    public List<Slot> findByNameContainingIgnoreCase(String name) {
        return slotRepository.findByBoardNameContainingIgnoreCase(name);
    }
    public SlotService(SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    public List<Slot> findAll() {
        return slotRepository.findAll();
    }

    public int count() {
        return (int) slotRepository.count();
    }

    public Slot save(Slot slot) {
        return slotRepository.save(slot);
    }
    public void delete(Slot slot) {
        slotRepository.delete(slot);
    }


}
