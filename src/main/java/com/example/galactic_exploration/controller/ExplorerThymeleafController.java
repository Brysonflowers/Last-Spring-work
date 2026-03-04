package com.example.galactic_exploration.controller;

import com.example.galactic_exploration.model.Explorer;
import com.example.galactic_exploration.repository.ExplorerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/explorers")
public class ExplorerThymeleafController {

    @Autowired
    private ExplorerRepository explorerRepository;

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
    public String createExplorer(@ModelAttribute Explorer explorer) {
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
    public String updateExplorer(@PathVariable Long id, @ModelAttribute Explorer explorer) {
        explorer.setId(id);
        explorerRepository.save(explorer);
        return "redirect:/explorers";
    }

    @GetMapping("/delete/{id}")
    public String deleteExplorer(@PathVariable Long id) {
        explorerRepository.deleteById(id);
        return "redirect:/explorers";
    }
}
