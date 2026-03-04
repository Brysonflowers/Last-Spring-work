package com.example.galactic_exploration.controller;

import com.example.galactic_exploration.model.Planet;
import com.example.galactic_exploration.repository.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/planets")
public class PlanetThymeleafController {

    @Autowired
    private PlanetRepository planetRepository;

    @GetMapping
    public String listPlanets(Model model) {
        model.addAttribute("planets", planetRepository.findAll());
        return "planets/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("planet", new Planet());
        return "planets/create";
    }

    @PostMapping
    public String createPlanet(@ModelAttribute Planet planet) {
        planetRepository.save(planet);
        return "redirect:/planets";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Planet planet = planetRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid planet Id:" + id));
        model.addAttribute("planet", planet);
        return "planets/edit";
    }

    @PostMapping("/update/{id}")
    public String updatePlanet(@PathVariable Long id, @ModelAttribute Planet planet) {
        planet.setId(id);
        planetRepository.save(planet);
        return "redirect:/planets";
    }

    @GetMapping("/delete/{id}")
    public String deletePlanet(@PathVariable Long id) {
        planetRepository.deleteById(id);
        return "redirect:/planets";
    }
}
