package com.example.galactic_exploration.controller;

import com.example.galactic_exploration.model.Explorer;
import com.example.galactic_exploration.model.Mission;
import com.example.galactic_exploration.repository.ExplorerRepository;
import com.example.galactic_exploration.repository.MissionRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/explorers")
public class ExplorerThymeleafController {

    @Autowired
    private ExplorerRepository explorerRepository;

    @Autowired
    private MissionRepository missionRepository;

    @GetMapping
    public String listExplorers(Model model) {
        model.addAttribute("explorers", explorerRepository.findAll());
        return "explorers/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("explorer", new Explorer());
        return "explorers/create";
    }

    @PostMapping
    public String createExplorer(@Valid @ModelAttribute Explorer explorer, BindingResult result) {
        if (result.hasErrors()) {
            return "explorers/create";
        }
        explorerRepository.save(explorer);
        return "redirect:/explorers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Explorer explorer = explorerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid explorer Id:" + id));
        model.addAttribute("explorer", explorer);
        return "explorers/edit";
    }

    @PostMapping("/update/{id}")
    @Transactional
    public String updateExplorer(@PathVariable Long id, @Valid @ModelAttribute Explorer explorerDetails, BindingResult result) {
        if (result.hasErrors()) {
            return "explorers/edit";
        }
        Explorer explorer = explorerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid explorer Id:" + id));
        explorer.setName(explorerDetails.getName());
        explorer.setSpecialization(explorerDetails.getSpecialization());
        explorerRepository.save(explorer);
        return "redirect:/explorers";
    }

    @GetMapping("/delete/{id}")
    @Transactional
    public String deleteExplorer(@PathVariable Long id) {
        Explorer explorer = explorerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid explorer Id:" + id));
        // Remove explorer from all missions first (Mission is the owner side)
        List<Mission> missions = missionRepository.findAll();
        for (Mission mission : missions) {
            if (mission.getExplorers().contains(explorer)) {
                mission.getExplorers().remove(explorer);
                missionRepository.save(mission);
            }
        }
        explorerRepository.delete(explorer);
        return "redirect:/explorers";
    }
}
