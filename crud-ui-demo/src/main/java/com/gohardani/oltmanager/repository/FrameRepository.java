package com.gohardani.oltmanager.repository;

import com.gohardani.oltmanager.entity.Frame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FrameRepository extends JpaRepository<Frame, Long> {
    List<Frame> findByFrameNumberEquals(int frameNo);

}
