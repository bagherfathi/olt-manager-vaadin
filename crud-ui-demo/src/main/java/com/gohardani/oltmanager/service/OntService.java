package com.gohardani.oltmanager.service;

import com.gohardani.oltmanager.entity.Ont;
import com.gohardani.oltmanager.entity.Port;
import com.gohardani.oltmanager.repository.OntRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OntService {

    private final OntRepository ontRepository;
    public List<Ont> findBySerialNumberContainingIgnoreCase(String serialNumber) {
        return ontRepository.findBySerialNumberContainingIgnoreCase(serialNumber);
    }
    public List<Ont> findBySerialNumberContainingIgnoreCaseAndPortEquals(String serialNumber, Port port) {
        if(port==null)
            return ontRepository.findBySerialNumberContainingIgnoreCase(serialNumber);
        else
            return ontRepository.findBySerialNumberContainingIgnoreCaseAndPortEquals(serialNumber,port);
    }
    public OntService(OntRepository ontRepository) {
        this.ontRepository = ontRepository;
    }

    public List<Ont> findByPortEquals(Port port) {
        return ontRepository.findByPortEquals(port);
    }

    public List<Ont> findAll() {
        return ontRepository.findAll();
    }

    public int count() {
        return (int) ontRepository.count();
    }

    public Ont save(Ont ont) {
        return ontRepository.save(ont);
    }
    public void delete(Ont ont) {
        ontRepository.delete(ont);
    }


}
