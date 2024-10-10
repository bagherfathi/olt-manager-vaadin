package com.gohardani.oltmanager.repository;

import com.gohardani.oltmanager.entity.Olt;
import com.gohardani.oltmanager.entity.ServiceProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ServiceProfileRepository extends JpaRepository<ServiceProfile, Long> {
    List<ServiceProfile> findByProfileNameContainingIgnoreCase(String name);
    @Modifying
    @Transactional
    void deleteByOltEquals(Olt olt);
    List<ServiceProfile> findByOltEquals(Olt olt);
}
