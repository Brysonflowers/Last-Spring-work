package com.example.galactic_exploration.controller;

import com.example.galactic_exploration.model.Explorer;
import com.example.galactic_exploration.model.Mission;
import com.example.galactic_exploration.model.Planet;
import com.example.galactic_exploration.repository.ExplorerRepository;
import com.example.galactic_exploration.repository.MissionRepository;
import com.example.galactic_exploration.repository.PlanetRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@Controller
@RequestMapping("/missions")
public class MissionThymeleafController {

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private ExplorerRepository explorerRepository;

    @GetMapping
    public String listMissions(Model model) {
        model.addAttribute("missions", missionRepository.findAll());
        return "missions/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("mission", new Mission());
        model.addAttribute("planets", planetRepository.findAll());
        model.addAttribute("explorers", explorerRepository.findAll());
        return "missions/create";
    }

    @PostMapping
    @Transactional
    public String createMission(@Valid @ModelAttribute("mission") Mission missionDetails, 
                                BindingResult result,
                                @RequestParam(value = "explorers", required = false) List<Long> explorerIds,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("planets", planetRepository.findAll());
            model.addAttribute("explorers", explorerRepository.findAll());
            return "missions/create";
        }

        // Find the actual planet from the DB
        if (missionDetails.getPlanet() != null && missionDetails.getPlanet().getId() != null) {
            Planet planet = planetRepository.findById(missionDetails.getPlanet().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid planet Id"));
            missionDetails.setPlanet(planet);
        }

        // Find actual explorers from the DB
        if (explorerIds != null && !explorerIds.isEmpty()) {
            List<Explorer> explorers = explorerRepository.findAllById(explorerIds);
            missionDetails.setExplorers(explorers);
        } else {
            missionDetails.setExplorers(new ArrayList<>());
        }

        missionRepository.save(missionDetails);
        return "redirect:/missions";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Mission mission = missionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid mission Id:" + id));
        model.addAttribute("mission", mission);
        model.addAttribute("planets", planetRepository.findAll());
        model.addAttribute("explorers", explorerRepository.findAll());
        return "missions/edit";
    }

    @PostMapping("/update/{id}")
    @Transactional
    public String updateMission(@PathVariable Long id, 
                                @Valid @ModelAttribute("mission") Mission missionDetails,
                                BindingResult result,
                                @RequestParam(value = "explorers", required = false) List<Long> explorerIds,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("planets", planetRepository.findAll());
            model.addAttribute("explorers", explorerRepository.findAll());
            return "missions/edit";
        }

        Mission mission = missionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid mission Id:" + id));
        
        mission.setObjective(missionDetails.getObjective());
        
        // Update Planet
        if (missionDetails.getPlanet() != null && missionDetails.getPlanet().getId() != null) {
            Planet planet = planetRepository.findById(missionDetails.getPlanet().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid planet Id"));
            mission.setPlanet(planet);
        }

        // Update Explorers
        if (explorerIds != null && !explorerIds.isEmpty()) {
            List<Explorer> explorers = explorerRepository.findAllById(explorerIds);
            mission.setExplorers(explorers);
        } else {
            mission.setExplorers(new ArrayList<>());
        }

        missionRepository.save(mission);
        return "redirect:/missions";
    }

    @GetMapping("/delete/{id}")
    public String deleteMission(@PathVariable Long id) {
        missionRepository.deleteById(id);
        return "redirect:/missions";
    }
}
