package com.esantefutur.esantefutur.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoriqueMedical {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistorique;

    @ManyToOne
    @JoinColumn(name = "id_patient")
    private Patient patient;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    @ElementCollection
    private List<String> documents;
}
