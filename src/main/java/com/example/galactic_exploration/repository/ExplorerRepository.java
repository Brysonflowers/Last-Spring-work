package com.example.galactic_exploration.repository;

import com.example.galactic_exploration.model.Explorer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExplorerRepository extends JpaRepository<Explorer, Long> {
}
