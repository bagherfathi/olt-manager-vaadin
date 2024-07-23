package com.gohardani.oltmanager.service;

import com.gohardani.oltmanager.entity.OltType;
import com.gohardani.oltmanager.repository.OltTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OltTypeService {

    private final OltTypeRepository oltTypeRepository;
    public List<OltType> findByNameContainingIgnoreCase(String name) {
        return oltTypeRepository.findByNameContainingIgnoreCase(name);
    }
    public OltTypeService(OltTypeRepository oltTypeRepository) {
        this.oltTypeRepository = oltTypeRepository;
    }

    public List<OltType> findAll() {
        return oltTypeRepository.findAll();
    }

    public int count() {
        return (int) oltTypeRepository.count();
    }

    public OltType save(OltType oltType) {
        return oltTypeRepository.save(oltType);
    }
    public void delete(OltType oltType) {
        oltTypeRepository.delete(oltType);
    }


}
