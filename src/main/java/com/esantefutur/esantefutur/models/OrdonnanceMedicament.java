package com.esantefutur.esantefutur.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdonnanceMedicament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ordonnance_id")
    private Ordonnance ordonnance;

    @ManyToOne
    @JoinColumn(name = "medicament_id")
    private Medicament medicament;

    private String dosagePrescrit;
    private String frequence;
    private String duree;
    private String instructionsSpeciales;
}
