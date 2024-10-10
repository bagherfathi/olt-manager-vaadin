package com.gohardani.oltmanager.repository;

import com.gohardani.oltmanager.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area, Long> {
    List<Area> findByNameContainingIgnoreCase(String name);

}
