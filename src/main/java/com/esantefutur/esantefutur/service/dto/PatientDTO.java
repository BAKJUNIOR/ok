package com.esantefutur.esantefutur.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO extends PersonDTO {

    private Long idPerson;
    private String groupeSanguin;
    private String numeroAssurance;
    private float poids;
    private float taille;
    private float imc;
    private String etatImc;
}
