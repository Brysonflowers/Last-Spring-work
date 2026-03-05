package com.example.galactic_exploration.controller;

import com.example.galactic_exploration.model.Planet;
import com.example.galactic_exploration.repository.PlanetRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String createPlanet(@Valid @ModelAttribute Planet planet, BindingResult result) {
        if (result.hasErrors()) {
            return "planets/create";
        }
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
    @Transactional
    public String updatePlanet(@PathVariable Long id, @Valid @ModelAttribute Planet planetDetails, BindingResult result) {
        if (result.hasErrors()) {
            return "planets/edit";
        }
        Planet planet = planetRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid planet Id:" + id));
        planet.setName(planetDetails.getName());
        planet.setClimate(planetDetails.getClimate());
        planetRepository.save(planet);
        return "redirect:/planets";
    }

    @GetMapping("/delete/{id}")
    public String deletePlanet(@PathVariable Long id) {
        planetRepository.deleteById(id);
        return "redirect:/planets";
    }
}
