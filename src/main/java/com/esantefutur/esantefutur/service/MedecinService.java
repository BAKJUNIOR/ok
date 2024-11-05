package com.esantefutur.esantefutur.service;


import com.esantefutur.esantefutur.models.Medecin;
import com.esantefutur.esantefutur.service.dto.MedecinDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MedecinService {

    Optional<Medecin> getMedecinByUserId(Long userId);
    MedecinDTO getAMedecinById(Long id);
    List<MedecinDTO> getAllMedecin();
    void deleteMedecin(Long id);

    void activation(Map<String, String> activation);

    MedecinDTO registerMedecin(MedecinDTO medecinDTO);

}
