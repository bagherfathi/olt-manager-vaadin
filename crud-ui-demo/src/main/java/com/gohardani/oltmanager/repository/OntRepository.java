package com.gohardani.oltmanager.repository;

import com.gohardani.oltmanager.entity.Ont;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OntRepository extends JpaRepository<Ont, Long> {
    List<Ont> findBySerialNumberContainingIgnoreCase(String serialNumber);

}
