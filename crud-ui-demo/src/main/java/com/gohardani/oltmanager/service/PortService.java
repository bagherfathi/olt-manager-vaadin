package com.gohardani.oltmanager.service;

import com.gohardani.oltmanager.entity.Port;
import com.gohardani.oltmanager.repository.PortRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortService {

    private final PortRepository portRepository;
    public List<Port> findByFspContainingIgnoreCase(String fsp) {
        return portRepository.findByFspContainingIgnoreCase(fsp);
    }
    public PortService(PortRepository portRepository) {
        this.portRepository = portRepository;
    }

    public List<Port> findAll() {
        return portRepository.findAll();
    }

    public int count() {
        return (int) portRepository.count();
    }

    public Port save(Port port) {
        return portRepository.save(port);
    }
    public void delete(Port port) {
        portRepository.delete(port);
    }


}
