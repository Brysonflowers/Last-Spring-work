package com.example.galactic_exploration.controller;

import com.example.galactic_exploration.model.Mission;
import com.example.galactic_exploration.repository.ExplorerRepository;
import com.example.galactic_exploration.repository.MissionRepository;
import com.example.galactic_exploration.repository.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String createMission(@ModelAttribute Mission mission) {
        missionRepository.save(mission);
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
    public String updateMission(@PathVariable Long id, @ModelAttribute Mission mission) {
        mission.setId(id);
        missionRepository.save(mission);
        return "redirect:/missions";
    }

    @GetMapping("/delete/{id}")
    public String deleteMission(@PathVariable Long id) {
        missionRepository.deleteById(id);
        return "redirect:/missions";
    }
}
