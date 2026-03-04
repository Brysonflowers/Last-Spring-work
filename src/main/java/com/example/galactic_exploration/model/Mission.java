package com.example.galactic_exploration.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String objective;

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
