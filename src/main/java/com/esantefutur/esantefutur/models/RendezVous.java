package com.esantefutur.esantefutur.models;

import com.esantefutur.esantefutur.models.enums.Statut;
import com.esantefutur.esantefutur.models.enums.TypeConsultation;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "rendez_vous")
public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRendezVous;

    @ManyToOne
    @JoinColumn(name = "id_patient")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "id_medecin")
    private Medecin medecin;

    @OneToOne
    @JoinColumn(name = "disponibilite_id")
    private Disponibilite disponibilite;

    @OneToOne
    @JoinColumn(name = "paiement_id")
    private Paiement paiement;

    @OneToOne(mappedBy = "rendezVous")
    private Consultation consultation;

    private LocalDateTime dateHeure;

    @Enumerated(EnumType.STRING)
    private Statut statut;

    @Enumerated(EnumType.STRING)
    private TypeConsultation typeConsultation;
}