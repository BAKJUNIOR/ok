package com.esantefutur.esantefutur.models;

import com.esantefutur.esantefutur.models.enums.Statut;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@Entity
public class Avis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAvis;

    @ManyToOne
    @JoinColumn(name = "id_patient")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "id_medecin")
    private Medecin medecin;

    @OneToOne
    @JoinColumn(name = "id_consultation")
    private Consultation consultation;

    private int note;
    private String commentaire;
    private Instant dateAvis;

    @Enumerated(EnumType.STRING)
    private Statut statut;
}