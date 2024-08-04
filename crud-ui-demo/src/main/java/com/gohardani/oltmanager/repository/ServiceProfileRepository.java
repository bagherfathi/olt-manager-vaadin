package com.gohardani.oltmanager.repository;

import com.gohardani.oltmanager.entity.ServiceProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceProfileRepository extends JpaRepository<ServiceProfile, Long> {
    List<ServiceProfile> findByProfileNameContainingIgnoreCase(String name);
}
