package com.example.galactic_exploration.controller;

import com.example.galactic_exploration.model.Planet;
import com.example.galactic_exploration.repository.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planets")
public class PlanetRestController {

    @Autowired
    private PlanetRepository planetRepository;

    @GetMapping
    public List<Planet> getAllPlanets() {
        return planetRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Planet> getPlanetById(@PathVariable Long id) {
        return planetRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Planet createPlanet(@RequestBody Planet planet) {
        return planetRepository.save(planet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Planet> updatePlanet(@PathVariable Long id, @RequestBody Planet planetDetails) {
        return planetRepository.findById(id).map(planet -> {
            planet.setName(planetDetails.getName());
            planet.setClimate(planetDetails.getClimate());
            return ResponseEntity.ok(planetRepository.save(planet));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanet(@PathVariable Long id) {
        return planetRepository.findById(id).map(planet -> {
            planetRepository.delete(planet);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
