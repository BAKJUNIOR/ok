package com.esantefutur.esantefutur.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "contenu_educatif")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContenuEducatif {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idContenu;

    private String titre;
    
    @Column(length = 1000)
    private String description;
    
    @Column(length = 5000)
    private String contenu;
    
    private String categorie;
    
    private String auteur;
    
    @Temporal(TemporalType.DATE)
    private Instant datePublication;
    
    private String statut;
    
    private String tags;
    
    private String imagePrincipale;
}
