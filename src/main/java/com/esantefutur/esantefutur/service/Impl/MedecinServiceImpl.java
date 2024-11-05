package com.esantefutur.esantefutur.service.Impl;

import com.esantefutur.esantefutur.models.*;
import com.esantefutur.esantefutur.models.enums.RoleName;
import com.esantefutur.esantefutur.repositories.MedecinRepository;
import com.esantefutur.esantefutur.repositories.UserRepository;
import com.esantefutur.esantefutur.repositories.UserRoleRepository;
import com.esantefutur.esantefutur.service.MedecinService;
import com.esantefutur.esantefutur.service.SpecialiteService;
import com.esantefutur.esantefutur.service.ValidationCodeService;
import com.esantefutur.esantefutur.service.dto.MedecinDTO;
import com.esantefutur.esantefutur.service.dto.SpecialiteDTO;
import com.esantefutur.esantefutur.service.dto.UserDTO;
import com.esantefutur.esantefutur.service.dto.ValidationCodeDTO;
import com.esantefutur.esantefutur.service.mappers.MedecinMapper;
import com.esantefutur.esantefutur.service.mappers.UserMapper;
import com.esantefutur.esantefutur.utils.SlugifyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedecinServiceImpl implements MedecinService {
    private final MedecinRepository medecinRepository;
    private final SpecialiteService serviceRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final MedecinMapper medecinMapper;
    private final ValidationCodeService validationCodeService;
    private final UserMapper userMapper;
    private final SpecialiteService specialiteService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Optional<Medecin> getMedecinByUserId(Long userId) {
        return medecinRepository.findByUser_Id(userId);
    }

    @Override
    public MedecinDTO getAMedecinById(Long id) {
        log.debug("Request to get Medecin by ID: {}", id);
        Optional<Medecin> medecinOptional = medecinRepository.findById(id);
        if (medecinOptional.isEmpty()) {
            throw new RuntimeException("Medecin not found with ID: " + id);
        }
        MedecinDTO medecinDTO = medecinMapper.fromEntity(medecinOptional.get());
        log.debug("Medecin found: {}", medecinDTO);
        return medecinDTO;
    }

    @Override
    public List<MedecinDTO> getAllMedecin() {
        log.debug("Request to get all Medecins");
        List<Medecin> medecins = medecinRepository.findAll();
        List<MedecinDTO> medecinDTOs = medecins.stream()
                .map(medecinMapper::fromEntity)
                .collect(Collectors.toList());
        log.debug("Total Medecins found: {}", medecinDTOs.size());
        return medecinDTOs;
    }

    @Override
    public void deleteMedecin(Long id) {
        log.debug("Request to delete Medecin with ID: {}", id);
        if (!medecinRepository.existsById(id)) {
            throw new RuntimeException("Medecin not found with ID: " + id);
        }
        medecinRepository.deleteById(id);
        log.debug("Medecin deleted successfully: {}", id);
    }

    @Override
    @Transactional
    public MedecinDTO registerMedecin(MedecinDTO medecinDTO) {
        log.debug("Request to register new Medecin: {}", medecinDTO);

        if (medecinDTO.getSpecialites() == null || medecinDTO.getSpecialites().isEmpty()) {
            throw new RuntimeException("Au moins une spécialité doit être spécifiée.");
        }

//        Set<MedecinSpecialite> medecinSpecialites = medecinDTO.getSpecialites().stream()
//                .map(specialiteDTO -> {
//                    Specialite specialite = specialiteService.getSpecialiteEntityById(specialiteDTO.getIdSpecialite())
//                            .orElseThrow(() -> new RuntimeException("Spécialité non trouvée avec ID: " + specialiteDTO.getIdSpecialite()));
//
//                    MedecinSpecialite medecinSpecialite = new MedecinSpecialite();
//                    medecinSpecialite.setSpecialite(specialite);
//                    medecinSpecialite.setMedecin(null);
//                    return medecinSpecialite;
//                })
//                .collect(Collectors.toSet());


        UserRole medecinRole = userRoleRepository.findByLibelle(RoleName.MEDECIN)
                .orElseGet(() -> {
                    log.debug("Rôle de médecin non trouvé, création d'un nouveau rôle");
                    UserRole newRole = new UserRole();
                    newRole.setLibelle(RoleName.MEDECIN);
                    return userRoleRepository.save(newRole);
                });

        User user = userMapper.toEntity(medecinDTO.getUser());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setDateCreation(Instant.now());
        user.setRoles(Set.of(medecinRole));
        user.setActive(true);

        if (!user.getEmail().contains("@") || !user.getEmail().contains(".")) {
            throw new RuntimeException("L'email est invalide");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Votre email est déjà utilisé");
        }

        User savedUser = userRepository.save(user);
        log.debug("User saved successfully: {}", savedUser);

        Medecin medecin = medecinMapper.toEntity(medecinDTO);
        medecin.setUser(savedUser);
        medecin.setSlug(SlugifyUtils.generate(medecinDTO.getFirstName()));
        medecin.setGender(medecinDTO.getGender());
        medecin.setFirstName(medecinDTO.getFirstName());
        medecin.setLastName(medecinDTO.getLastName());
//        medecin.setMedecinSpecialites((HashSet<MedecinSpecialite>) medecinSpecialites); // Association des spécialités

        Medecin savedMedecin = medecinRepository.save(medecin);
        log.debug("Medecin registered successfully: {}", savedMedecin);

        // Conversion en DTO
        MedecinDTO savedMedecinDTO = medecinMapper.fromEntity(savedMedecin);
        savedMedecinDTO.setUser(userMapper.fromEntity(savedUser));
        UserDTO userDTO = userMapper.fromEntity(savedUser);
        this.validationCodeService.enregistrerCode(userDTO);
        log.debug("Validation code registered for user: {}", userDTO);

        return savedMedecinDTO;
    }


    public void activation(Map<String, String> activation) {
        log.debug("Request to activate user with code: {}", activation.get(" code"));
        ValidationCodeDTO validationCodeDTO = validationCodeService.lireEnFonctionDuCode(activation.get("code"));
        log.debug("Validation code found: {}", validationCodeDTO);

        if (Instant.now().isAfter(validationCodeDTO.getExpiration())) {
            throw new RuntimeException("Votre code a expiré");
        }
        User userActiver = this.userRepository.findById(validationCodeDTO.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Utilisateur inconnu"));
        log.debug("User to activate found: {}", userActiver);

        userActiver.setActive(true);
        userRepository.save(userActiver);
        log.debug("User activated successfully: {}", userActiver);
    }


//    @Override
//    public List<MedecinDTO> findMedecinsBySpecialite(Long specialiteId) {
//        log.debug("Request to find Medecins by Specialite ID: {}", specialiteId);
//
//        List<Medecin> medecins = medecinRepository.findByMedecinSpecialites(specialiteId);
//
//        List<MedecinDTO> medecinDTOs = medecins.stream()
//                .map(medecinMapper::fromEntity)
//                .collect(Collectors.toList());
//        log.debug("Total Medecins found for Specialite ID {}: {}", specialiteId, medecinDTOs.size());
//        return medecinDTOs;
//    }
}
