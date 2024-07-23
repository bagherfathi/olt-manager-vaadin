package com.gohardani.oltmanager.repository;

import com.gohardani.oltmanager.entity.OltType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OltTypeRepository extends JpaRepository<OltType, Long> {
    List<OltType> findByNameContainingIgnoreCase(String name);

}
