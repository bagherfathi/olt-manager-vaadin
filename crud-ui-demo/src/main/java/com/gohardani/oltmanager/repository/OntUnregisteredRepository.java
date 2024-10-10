package com.gohardani.oltmanager.repository;

import com.gohardani.oltmanager.entity.Olt;
import com.gohardani.oltmanager.entity.Ont;
import com.gohardani.oltmanager.entity.OntUnregistered;
import com.gohardani.oltmanager.entity.Port;
import com.gohardani.oltmanager.ui.view.UnregisteredONTView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OntUnregisteredRepository extends JpaRepository<OntUnregistered, Long> {
    List<OntUnregistered> findBySerialNumberContainingIgnoreCase(String serialNumber);
    List<OntUnregistered> findByFspContainingIgnoreCase(String fsp);
    @Modifying
    @Transactional
    void deleteByOltEquals(Olt olt);
    List<OntUnregistered> findByOltEquals(Olt olt);
}