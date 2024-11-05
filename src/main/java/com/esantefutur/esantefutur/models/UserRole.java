package com.esantefutur.esantefutur.models;

import com.esantefutur.esantefutur.models.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class UserRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_role;
    @Enumerated(EnumType.STRING)
    private RoleName libelle;



}