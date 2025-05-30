package com.gohardani.oltmanager.service;

import com.gohardani.oltmanager.entity.Olt;
import com.gohardani.oltmanager.entity.ServiceProfile;
import com.gohardani.oltmanager.repository.ServiceProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceProfileService {

    private final ServiceProfileRepository serviceProfileRepository;
    public List<ServiceProfile> findByProfileNameContainingIgnoreCase(String name) {
        return serviceProfileRepository.findByProfileNameContainingIgnoreCase(name);
    }
    public ServiceProfileService(ServiceProfileRepository serviceProfileRepository) {
        this.serviceProfileRepository = serviceProfileRepository;
    }

    public List<ServiceProfile> findAll() {
        return serviceProfileRepository.findAll();
    }

    public int count() {
        return (int) serviceProfileRepository.count();
    }

    public ServiceProfile save(ServiceProfile serviceProfile) {
        return serviceProfileRepository.save(serviceProfile);
    }
    public void delete(ServiceProfile serviceProfile) {serviceProfileRepository.delete(serviceProfile);}
    public void deleteByOlt(Olt olt) {serviceProfileRepository.deleteByOltEquals(olt);}
    public List<ServiceProfile> findByOltEquals(Olt olt) {return serviceProfileRepository.findByOltEquals(olt);}
}
