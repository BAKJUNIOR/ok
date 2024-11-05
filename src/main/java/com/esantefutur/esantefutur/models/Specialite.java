package com.esantefutur.esantefutur.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Specialite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSpecialite;

    private String nom;
    private String description;
    private String icone;

    @ManyToMany(mappedBy = "specialites")
    private List<Medecin> medecins;
}
