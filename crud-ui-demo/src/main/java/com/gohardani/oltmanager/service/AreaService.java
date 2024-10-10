package com.gohardani.oltmanager.service;

import com.gohardani.oltmanager.entity.Area;
import com.gohardani.oltmanager.repository.AreaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaService {

    private final AreaRepository areaRepository;
    public List<Area> findByNameContainingIgnoreCase(String name) {
        return areaRepository.findByNameContainingIgnoreCase(name);
    }
    public AreaService(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    public List<Area> findAll() {
        return areaRepository.findAll();
    }

    public int count() {
        return (int) areaRepository.count();
    }

    public Area save(Area area) {
        return areaRepository.save(area);
    }
    public void delete(Area area) {
        areaRepository.delete(area);
    }


}
