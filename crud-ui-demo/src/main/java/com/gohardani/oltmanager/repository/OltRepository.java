package com.gohardani.oltmanager.repository;

import com.gohardani.oltmanager.entity.Olt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OltRepository extends JpaRepository<Olt, Long> {
    List<Olt> findByNameContainingIgnoreCase(String name);

}
