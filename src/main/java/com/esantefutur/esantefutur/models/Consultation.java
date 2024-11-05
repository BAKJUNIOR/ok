package com.esantefutur.esantefutur.models;

import com.esantefutur.esantefutur.models.enums.Statut;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConsultation;

    @OneToOne
    @JoinColumn(name = "id_rendezvous")
    private RendezVous rendezVous;

    private LocalDateTime dateHeureDebut;
    private LocalDateTime dateHeureFin;
    private String diagnostic;
    private String observations;
    private String recommandations;

    @Enumerated(EnumType.STRING)
    private Statut statut;
}