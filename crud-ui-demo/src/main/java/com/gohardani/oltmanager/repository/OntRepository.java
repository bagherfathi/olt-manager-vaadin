package com.gohardani.oltmanager.repository;

import com.gohardani.oltmanager.entity.Ont;
import com.gohardani.oltmanager.entity.Port;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OntRepository extends JpaRepository<Ont, Long> {
    List<Ont> findBySerialNumberContainingIgnoreCase(String serialNumber);

    List<Ont> findBySerialNumberContainingIgnoreCaseAndPortEquals(String serialNumber, Port port);

    List<Ont> findByPortEquals(Port port);

    @Modifying
    @Transactional
    void deleteByPortEquals(Port port);
}
