package com.esantefutur.esantefutur.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
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
public class Ordonnance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrdonnance;

    @OneToOne
    @JoinColumn(name = "id_consultation")
    private Consultation consultation;

    private LocalDate datePrescription;

    @Column(columnDefinition = "TEXT")
    private String details;

    @Column(columnDefinition = "TEXT")
    private String notes;

    private LocalDate validite;

    @OneToMany(mappedBy = "ordonnance")
    private List<OrdonnanceMedicament> medicaments;
}
