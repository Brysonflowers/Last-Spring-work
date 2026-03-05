package com.example.galactic_exploration.controller;

import com.example.galactic_exploration.model.Mission;
import com.example.galactic_exploration.repository.MissionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/missions")
public class MissionRestController {

    @Autowired
    private MissionRepository missionRepository;

    @GetMapping
    public List<Mission> getAllMissions() {
        return missionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mission> getMissionById(@PathVariable Long id) {
        return missionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mission createMission(@Valid @RequestBody Mission mission) {
        return missionRepository.save(mission);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mission> updateMission(@PathVariable Long id, @Valid @RequestBody Mission missionDetails) {
        return missionRepository.findById(id).map(mission -> {
            mission.setObjective(missionDetails.getObjective());
            mission.setPlanet(missionDetails.getPlanet());
            mission.setExplorers(missionDetails.getExplorers());
            return ResponseEntity.ok(missionRepository.save(mission));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMission(@PathVariable Long id) {
        return missionRepository.findById(id).map(mission -> {
            missionRepository.delete(mission);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
