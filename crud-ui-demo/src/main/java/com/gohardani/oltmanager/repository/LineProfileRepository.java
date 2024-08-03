package com.gohardani.oltmanager.repository;

import com.gohardani.oltmanager.entity.LineProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LineProfileRepository extends JpaRepository<LineProfile, Long> {
    List<LineProfile> findByProfileNameContainingIgnoreCase(String name);
}
