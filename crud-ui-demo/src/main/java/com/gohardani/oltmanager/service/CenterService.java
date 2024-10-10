package com.gohardani.oltmanager.service;

import com.gohardani.oltmanager.entity.Area;
import com.gohardani.oltmanager.entity.Center;
import com.gohardani.oltmanager.repository.AreaRepository;
import com.gohardani.oltmanager.repository.CenterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CenterService {

    private final CenterRepository centerRepository;
    public List<Center> findByNameContainingIgnoreCase(String name) {
        return centerRepository.findByNameContainingIgnoreCase(name);
    }
    public CenterService(CenterRepository centerRepository) {
        this.centerRepository = centerRepository;
    }

    public List<Center> findAll() {
        return centerRepository.findAll();
    }
    public List<Center> findByArea(Area area) {return centerRepository.findByAreaEquals(area);}

    public int count() {
        return (int) centerRepository.count();
    }

    public Center save(Center center) {
        return centerRepository.save(center);
    }
    public void delete(Center center) {
        centerRepository.delete(center);
    }


}
