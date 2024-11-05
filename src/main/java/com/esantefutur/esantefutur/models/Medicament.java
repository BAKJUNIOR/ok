package com.esantefutur.esantefutur.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMedicament;

    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String categorie;
    private String dosageStandard;

    @Column(columnDefinition = "TEXT")
    private String precautions;

    @OneToMany(mappedBy = "medicament")
    private List<OrdonnanceMedicament> ordonnances;
}
