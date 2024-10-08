package com.gohardani.oltmanager.service;

import com.gohardani.oltmanager.entity.Olt;
import com.gohardani.oltmanager.entity.OltParameters;
import com.gohardani.oltmanager.repository.OltParametersRepository;
import com.gohardani.oltmanager.repository.OltRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OltParametersService {

    private final OltParametersRepository oltParametersRepository;

    public List<OltParameters> findOltParametersByOlt(Olt olt) {
        if(olt==null)
            return oltParametersRepository.findAll();
        return oltParametersRepository.findByOltEquals(olt);
    }
    public OltParametersService(OltParametersRepository oltParametersRepository) {
        this.oltParametersRepository = oltParametersRepository;
    }

    public List<OltParameters> findAll() {
        return oltParametersRepository.findAll();
    }

    public int count() {
        return (int) oltParametersRepository.count();
    }

    public OltParameters save(OltParameters oltParameters) {
        return oltParametersRepository.save(oltParameters);
    }
    public void delete(OltParameters oltParameters) {
        oltParametersRepository.delete(oltParameters);
    }
}
