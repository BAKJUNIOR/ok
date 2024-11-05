package com.esantefutur.esantefutur.models;

import com.esantefutur.esantefutur.models.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_personne")
    private Long idPerson;


    @NotBlank(message = "Le prénom est obligatoire")
    @Column(name = "firstName", nullable = false)
    private String firstName;

    @NotBlank(message = "Le nom de famille est obligatoire")
    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "numeroTelephone")
    private String numbers;


    @NotNull(message = "La date de naissance est obligatoire")
    @Column(name = "dateOfBirth")
    private LocalDate dateOfBirth;

    @Column(name = "url_picture")
    private String photoProfil;

    @Column(unique = true)
    private String slug;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Le genre est obligatoire")
    private Gender gender;

    private String adresse;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    private User user;

}
