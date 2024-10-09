package com.gohardani.oltmanager.service;

import com.gohardani.oltmanager.entity.LineProfile;
import com.gohardani.oltmanager.entity.Olt;
import com.gohardani.oltmanager.repository.LineProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LineProfileService {

    private final LineProfileRepository lineProfileRepository;
    public List<LineProfile> findByProfileNameContainingIgnoreCase(String name) {
        return lineProfileRepository.findByProfileNameContainingIgnoreCase(name);
    }
    public LineProfileService(LineProfileRepository lineProfileRepository) {
        this.lineProfileRepository = lineProfileRepository;
    }

    public List<LineProfile> findAll() {
        return lineProfileRepository.findAll();
    }

    public int count() {
        return (int) lineProfileRepository.count();
    }

    public LineProfile save(LineProfile lineProfile) {
        return lineProfileRepository.save(lineProfile);
    }
    public void delete(LineProfile lineprofile) {
        lineProfileRepository.delete(lineprofile);
    }
    public void deleteByOlt(Olt olt) {lineProfileRepository.deleteByOltEquals(olt);}
}
