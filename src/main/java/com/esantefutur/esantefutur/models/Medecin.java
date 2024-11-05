package com.esantefutur.esantefutur.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "medecins")
public class Medecin extends Person {

    private String numeroLicence;
    private int anneesExperience;
    private double tarifConsultation;
    private String biographie;

    @OneToMany(mappedBy = "medecin")
    private List<Disponibilite> disponibilites;

    @OneToMany(mappedBy = "medecin")
    private List<RendezVous> rendezVous;

    @ManyToMany
    @JoinTable(
            name = "medecin_specialite",
            joinColumns = @JoinColumn(name = "medecin_id"),
            inverseJoinColumns = @JoinColumn(name = "specialite_id")
    )
    private List<Specialite> specialites;

    @OneToMany(mappedBy = "medecin")
    private List<Avis> avis;
}
