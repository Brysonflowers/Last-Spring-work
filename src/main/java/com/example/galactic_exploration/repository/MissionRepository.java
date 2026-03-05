package com.example.galactic_exploration.repository;

import com.example.galactic_exploration.model.Mission;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {
    
    @Override
    @EntityGraph(attributePaths = {"planet", "explorers"})
    List<Mission> findAll();
}
