package com.gohardani.oltmanager.repository;

import com.gohardani.oltmanager.entity.Ont;
import com.gohardani.oltmanager.entity.OntUnregistered;
import com.gohardani.oltmanager.entity.Port;
import com.gohardani.oltmanager.ui.view.UnregisteredONTView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OntUnregisteredRepository extends JpaRepository<OntUnregistered, Long> {
    List<OntUnregistered> findBySerialNumberContainingIgnoreCase(String serialNumber);
    List<OntUnregistered> findByFspContainingIgnoreCase(String fsp);
}