package com.example.galactic_exploration;

import com.example.galactic_exploration.model.Explorer;
import com.example.galactic_exploration.model.Mission;
import com.example.galactic_exploration.model.Planet;
import com.example.galactic_exploration.repository.ExplorerRepository;
import com.example.galactic_exploration.repository.MissionRepository;
import com.example.galactic_exploration.repository.PlanetRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PlanetRepository planetRepository;
    private final ExplorerRepository explorerRepository;
    private final MissionRepository missionRepository;

    public DataInitializer(PlanetRepository planetRepository,
                           ExplorerRepository explorerRepository,
                           MissionRepository missionRepository) {
        this.planetRepository = planetRepository;
        this.explorerRepository = explorerRepository;
        this.missionRepository = missionRepository;
    }

    @Override
    @jakarta.transaction.Transactional
    public void run(String... args) {
        if (planetRepository.count() == 0) {
            Planet mars = planetRepository.save(Planet.builder().name("Mars").climate("Arid").build());
            Planet europa = planetRepository.save(Planet.builder().name("Europa").climate("Icy").build());

            Explorer neil = explorerRepository.save(Explorer.builder().name("Neil").specialization("Navigation").build());
            Explorer buzz = explorerRepository.save(Explorer.builder().name("Buzz").specialization("Engineering").build());

            missionRepository.save(Mission.builder()
                    .objective("Search for Water")
                    .planet(mars)
                    .explorers(Arrays.asList(neil, buzz))
                    .build());
        }
    }
}
