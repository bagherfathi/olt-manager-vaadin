package com.gohardani.oltmanager.repository;

import com.gohardani.oltmanager.entity.LineProfile;
import com.gohardani.oltmanager.entity.Olt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LineProfileRepository extends JpaRepository<LineProfile, Long> {
    List<LineProfile> findByProfileNameContainingIgnoreCase(String name);
    @Modifying
    @Transactional
    void deleteByOltEquals(Olt olt);
}
