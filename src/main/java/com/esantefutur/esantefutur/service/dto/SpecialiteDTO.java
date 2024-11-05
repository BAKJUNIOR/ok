package com.esantefutur.esantefutur.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecialiteDTO {
    private Long idSpecialite;
    private String nom;
    private String description;
    private String icone;
    private List<MedecinDTO> medecins;
}
