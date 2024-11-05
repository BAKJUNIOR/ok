package com.esantefutur.esantefutur.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedecinDTO extends PersonDTO {

    private Long idPerson;
    private String numeroLicence;
    private int anneesExperience;
    private double tarifConsultation;
    private String biographie;
    private List<SpecialiteDTO> specialites;
}