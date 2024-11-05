package com.esantefutur.esantefutur.service.Impl;

import com.esantefutur.esantefutur.models.Specialite;
import com.esantefutur.esantefutur.repositories.SpecialiteRepository;
import com.esantefutur.esantefutur.service.SpecialiteService;
import com.esantefutur.esantefutur.service.dto.MedecinDTO;
import com.esantefutur.esantefutur.service.dto.SpecialiteDTO;
import com.esantefutur.esantefutur.service.mappers.MedecinMapper;
import com.esantefutur.esantefutur.service.mappers.SpecialiteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpecialiteServiceImpl implements SpecialiteService {

    private final SpecialiteRepository specialiteRepository;
    private final SpecialiteMapper specialiteMapper;
    private final MedecinMapper medecinMapper;

    @Override
    public SpecialiteDTO createSpecialite(SpecialiteDTO specialiteDTO) {
        log.debug("Création d'une nouvelle spécialité: {}", specialiteDTO);
        Specialite specialite = specialiteMapper.toEntity(specialiteDTO);
        specialiteRepository.save(specialite);
        return specialiteDTO;
    }

    @Override
    public SpecialiteDTO getSpecialiteById(Long id) {
        log.debug("Récupération de la spécialité avec ID: {}", id);
        Specialite specialite = specialiteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Spécialité non trouvée: " + id));
        return specialiteMapper.fromEntity(specialite);
    }

    @Override
    public List<SpecialiteDTO> getAllSpecialites() {
        log.debug("Récupération de toutes les spécialités");
        return specialiteRepository.findAll().stream()
                .map(specialiteMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public SpecialiteDTO updateSpecialite(Long id, SpecialiteDTO specialiteDTO) {
        log.debug("Mise à jour de la spécialité avec ID: {}", id);
        Specialite specialite = specialiteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Spécialité non trouvée: " + id));

        specialite.setNom(specialiteDTO.getNom());
        specialite.setDescription(specialiteDTO.getDescription());
        specialite.setIcone(specialiteDTO.getIcone());

        specialiteRepository.save(specialite);
        return specialiteDTO;
    }

    @Override
    public void deleteSpecialite(Long id) {
        log.debug("Suppression de la spécialité avec ID: {}", id);
        specialiteRepository.deleteById(id);
    }

    @Override
    public List<MedecinDTO> getMedecinsBySpecialite(Long specialiteId) {
        log.debug("Récupération des médecins pour la spécialité avec ID: {}", specialiteId);
        Specialite specialite = specialiteRepository.findById(specialiteId)
                .orElseThrow(() -> new RuntimeException("Spécialité non trouvée: " + specialiteId));

        return specialite.getMedecins().stream()
                .map(medecinMapper::fromEntity)
                .collect(Collectors.toList());
    }


    @Override
    public List<SpecialiteDTO> getMostCommonSpecialites() {
        log.debug("Obtention des spécialités les plus courantes");
        return specialiteRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Specialite::getNom,
                        Collectors.counting()
                )).entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue())) // Tri par fréquence
                .map(entry -> specialiteRepository.findByNom(entry.getKey()).orElseThrow())
                .map(specialiteMapper::fromEntity)
                .collect(Collectors.toList());
    }
}
