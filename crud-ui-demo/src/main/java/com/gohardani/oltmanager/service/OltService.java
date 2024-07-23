package com.gohardani.oltmanager.service;

import com.gohardani.oltmanager.entity.Olt;
import org.springframework.stereotype.Service;
import com.gohardani.oltmanager.repository.OltRepository;

import java.util.List;

@Service
public class OltService {

    private final OltRepository oltRepository;
    public List<Olt> findByNameContainingIgnoreCase(String name) {
        return oltRepository.findByNameContainingIgnoreCase(name);
    }
    public OltService(OltRepository oltRepository) {
        this.oltRepository = oltRepository;
    }

    public List<Olt> findAll() {
        return oltRepository.findAll();
    }

    public int count() {
        return (int) oltRepository.count();
    }

    public Olt save(Olt olt) {
        return oltRepository.save(olt);
    }
    public void delete(Olt olt) {
        oltRepository.delete(olt);
    }


}
