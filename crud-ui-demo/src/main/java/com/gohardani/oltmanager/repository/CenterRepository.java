package com.gohardani.oltmanager.repository;

import com.gohardani.oltmanager.entity.Area;
import com.gohardani.oltmanager.entity.Center;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CenterRepository extends JpaRepository<Center, Long> {
    List<Center> findByNameContainingIgnoreCase(String name);
    List<Center> findByAreaEquals(Area area);

}
