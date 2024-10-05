package com.gohardani.oltmanager.service;

import com.gohardani.oltmanager.entity.OntUnregistered;
import com.gohardani.oltmanager.repository.OntUnregisteredRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OntUnregisteredService {

    private final OntUnregisteredRepository ontUnregisteredRepository;
    public List<OntUnregistered> findBySerialNumberContainingIgnoreCase(String serialNumber) {
        return ontUnregisteredRepository.findBySerialNumberContainingIgnoreCase(serialNumber);
    }
    public List<OntUnregistered> findByFspContainingIgnoreCase(String fsp) {
        return ontUnregisteredRepository.findByFspContainingIgnoreCase(fsp);
    }
    public OntUnregisteredService(OntUnregisteredRepository ontUnregisteredRepository) {
        this.ontUnregisteredRepository = ontUnregisteredRepository;
    }

    public List<OntUnregistered> findAll() {
        return ontUnregisteredRepository.findAll();
    }

    public int count() {
        return (int) ontUnregisteredRepository.count();
    }

    public OntUnregistered save(OntUnregistered ontUnregistered) {
        return ontUnregisteredRepository.save(ontUnregistered);
    }
    public void delete(OntUnregistered ontUnregistered) {
        ontUnregisteredRepository.delete(ontUnregistered);
    }
    public void deleteAll(){
        ontUnregisteredRepository.deleteAll();
    }
    public void saveAll(List<OntUnregistered> ontUnregisteredList) {
        ontUnregisteredRepository.saveAll(ontUnregisteredList);
    }
}
