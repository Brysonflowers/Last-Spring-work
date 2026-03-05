package com.example.galactic_exploration.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Explorer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Explorer name is mandatory")
    @Column(nullable = false)
    private String name;

    private String specialization;

    @ManyToMany(mappedBy = "explorers")
    @JsonIgnore
    @Builder.Default
    private List<Mission> missions = new ArrayList<>();
}
