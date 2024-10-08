package com.gohardani.oltmanager.repository;

import com.gohardani.oltmanager.entity.Olt;
import com.gohardani.oltmanager.entity.OltParameters;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OltParametersRepository extends JpaRepository<OltParameters, Long> {
    List<OltParameters> findByOltEquals(Olt olt);

}
