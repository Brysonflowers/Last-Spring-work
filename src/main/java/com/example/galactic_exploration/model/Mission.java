package com.example.galactic_exploration.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mission objective is mandatory")
    @Column(nullable = false)
    private String objective;

    @NotNull(message = "Planet is mandatory")
    @ManyToOne(optional = false)
    @JoinColumn(name = "planet_id")
    private Planet planet;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
        name = "mission_explorer",
        joinColumns = @JoinColumn(name = "mission_id"),
        inverseJoinColumns = @JoinColumn(name = "explorer_id")
    )
    @Builder.Default
    private List<Explorer> explorers = new ArrayList<>();
}
