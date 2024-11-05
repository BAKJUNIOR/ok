package com.esantefutur.esantefutur.models;

import com.esantefutur.esantefutur.models.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "patients")
public class Patient extends Person {

    private String groupeSanguin;
    private String numeroAssurance;
    private float poids;
    private float taille;
    private Float imc;
    private String etatImc;

    @OneToMany(mappedBy = "patient")
    private List<RendezVous> rendezVous;

    @OneToMany(mappedBy = "patient")
    private List<HistoriqueMedical> historiquesMedicaux;

    @OneToMany(mappedBy = "patient")
    private List<Avis> avis;
}
