package com.esantefutur.esantefutur.service;

import com.esantefutur.esantefutur.service.dto.MedecinDTO;
import com.esantefutur.esantefutur.service.dto.SpecialiteDTO;

import java.util.List;

public interface SpecialiteService {
    SpecialiteDTO createSpecialite(SpecialiteDTO specialiteDTO);
    SpecialiteDTO getSpecialiteById(Long id);
    List<SpecialiteDTO> getAllSpecialites();
    List<SpecialiteDTO> getMostCommonSpecialites();
    SpecialiteDTO updateSpecialite(Long id, SpecialiteDTO specialiteDTO);
    void deleteSpecialite(Long id);

    List<MedecinDTO> getMedecinsBySpecialite(Long specialiteId);
}
