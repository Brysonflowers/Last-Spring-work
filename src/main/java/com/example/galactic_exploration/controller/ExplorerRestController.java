package com.example.galactic_exploration.controller;

import com.example.galactic_exploration.model.Explorer;
import com.example.galactic_exploration.repository.ExplorerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/explorers")
public class ExplorerRestController {

    @Autowired
    private ExplorerRepository explorerRepository;

    @GetMapping
    public List<Explorer> getAllExplorers() {
        return explorerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Explorer> getExplorerById(@PathVariable Long id) {
        return explorerRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Explorer createExplorer(@Valid @RequestBody Explorer explorer) {
        return explorerRepository.save(explorer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Explorer> updateExplorer(@PathVariable Long id, @Valid @RequestBody Explorer explorerDetails) {
        return explorerRepository.findById(id).map(explorer -> {
            explorer.setName(explorerDetails.getName());
            explorer.setSpecialization(explorerDetails.getSpecialization());
            return ResponseEntity.ok(explorerRepository.save(explorer));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExplorer(@PathVariable Long id) {
        return explorerRepository.findById(id).map(explorer -> {
            explorerRepository.delete(explorer);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
