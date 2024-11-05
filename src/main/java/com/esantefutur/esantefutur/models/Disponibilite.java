
package com.esantefutur.esantefutur.models;

import com.esantefutur.esantefutur.models.enums.Statut;
import com.esantefutur.esantefutur.models.enums.TypeConsultation;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;
@Data
@NoArgsConstructor
@Entity
public class Disponibilite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDisponibilite;

    @ManyToOne
    @JoinColumn(name = "id_medecin")
    private Medecin medecin;

    private LocalDate date;
    private LocalTime heureDebut;
    private LocalTime heureFin;

    @Enumerated(EnumType.STRING)
    private Statut statut;

    private int dureeConsultation;

    @OneToOne(mappedBy = "disponibilite")
    private RendezVous rendezVous;
}