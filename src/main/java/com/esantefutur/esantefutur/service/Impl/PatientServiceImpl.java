package com.esantefutur.esantefutur.service.Impl;

import com.esantefutur.esantefutur.models.Patient;
import com.esantefutur.esantefutur.models.User;
import com.esantefutur.esantefutur.models.UserRole;
import com.esantefutur.esantefutur.models.enums.RoleName;
import com.esantefutur.esantefutur.repositories.PatientRepository;
import com.esantefutur.esantefutur.repositories.UserRepository;
import com.esantefutur.esantefutur.repositories.UserRoleRepository;
import com.esantefutur.esantefutur.repositories.ValidationCodeRepository;
import com.esantefutur.esantefutur.service.PatientService;
import com.esantefutur.esantefutur.service.ValidationCodeService;
import com.esantefutur.esantefutur.service.dto.PatientDTO;
import com.esantefutur.esantefutur.service.dto.UserDTO;
import com.esantefutur.esantefutur.service.dto.ValidationCodeDTO;
import com.esantefutur.esantefutur.service.mappers.PatientMapper;
import com.esantefutur.esantefutur.service.mappers.UserMapper;
import com.esantefutur.esantefutur.utils.SlugifyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PatientMapper patientMapper;
    private final UserMapper userMapper;
    private final ValidationCodeService validationCodeService;
    private final ValidationCodeRepository validationCodeRepository;
    private final BCryptPasswordEncoder passwordEncoder;





    @Override
    public PatientDTO getPatientById(Long id) {
        log.debug("Request to get patient by ID: {}", id);

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        log.debug("Patient found: {}", patient);

        return patientMapper.fromEntity(patient);
    }

    @Override
    public Optional<Patient> getPatientByUserId(Long userId) {
        return patientRepository.findByUser_Id(userId);
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        log.debug("Request to get all patients");

        List<PatientDTO> patients = patientRepository.findAll().stream()
                .map(patientMapper::fromEntity)
                .collect(Collectors.toList());
        return patients;
    }

    @Override
    @Transactional
    public void deletePatient(Long patientId) {
        log.debug("Request to delete patient with ID: {}", patientId);

        if (!patientRepository.existsById(patientId)) {
            throw new RuntimeException("Patient not found");
        }
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        validationCodeRepository.deleteByUserId(patient.getUser().getId());

        patientRepository.deleteById(patientId);
        log.debug("Patient deleted successfully: {}", patientId);
    }

    @Override
    @Transactional
    public PatientDTO registerPatient(PatientDTO patientDTO) {
        log.debug("Request to register new patient: {}", patientDTO);

        UserRole patientRole = userRoleRepository.findByLibelle(RoleName.PATIENT)
                .orElseGet(() -> {
                    log.debug("Patient role not found, creating a new role");
                    UserRole newRole = new UserRole();
                    newRole.setLibelle(RoleName.PATIENT);
                    return userRoleRepository.save(newRole);
                });

        User user = userMapper.toEntity(patientDTO.getUser());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setDateCreation(Instant.now());
        user.setRoles(Set.of(patientRole));
        user.setUsername(patientDTO.getUser().getUsername());
        user.setEmail(patientDTO.getUser().getEmail());
        log.debug("User created for registration:{}", user);

        if (!user.getEmail().contains("@") || !user.getEmail().contains(".")) {
            throw new RuntimeException("Votre email est invalide");
        }
        Optional<User> utilisateurOptional = this.userRepository.findByEmail(user.getEmail());
        if (utilisateurOptional.isPresent()) {
            throw new RuntimeException("Votre email est déjà utilisé");
        }

        User savedUser = userRepository.save(user);
        log.debug("User saved successfully: {}", savedUser);

        Patient patient = patientMapper.toEntity(patientDTO);
        patient.setUser(savedUser);
        patient.setSlug(SlugifyUtils.generate(patientDTO.getFirstName()));
        patient.setGender(patientDTO.getGender());
        patient.setFirstName(patientDTO.getFirstName());
        patient.setLastName(patientDTO.getLastName());

        Patient savedPatient = patientRepository.save(patient);
        log.debug("Patient registered successfully: {}", savedPatient);

        PatientDTO savedPatientDTO = patientMapper.fromEntity(savedPatient);
        savedPatientDTO.setUser(userMapper.fromEntity(savedUser));
        UserDTO userDTO = userMapper.fromEntity(savedUser);
        this.validationCodeService.enregistrerCode(userDTO);
        log.debug("Validation code registered for user:{}", userDTO);

        return savedPatientDTO;
    }

    @Override
    public PatientDTO updateProfile(PatientDTO patientDTO) {
        log.debug("Updating profile for patient with ID: {}", patientDTO.getIdPerson());

        Patient patient = patientRepository.findById(patientDTO.getIdPerson())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        patient.setPoids(patientDTO.getPoids());
        patient.setTaille(patientDTO.getTaille());
        patient.setPhotoProfil(patientDTO.getPhotoProfil());
        patient.setGroupeSanguin(patientDTO.getGroupeSanguin());
        patient.setNumeroAssurance(patientDTO.getNumeroAssurance());
        patient.setAdresse(patientDTO.getAdresse());
        patient.setDateOfBirth(patientDTO.getDateOfBirth());
        patient.setNumbers(patientDTO.getNumbers());

        float imc = calculateImc(patient.getPoids(), patient.getTaille());
        patient.setImc(imc);
        patient.setEtatImc(determineImcState(imc));

        Patient updatedPatient = patientRepository.save(patient);
        log.debug("Patient profile updated with IMC: {} and state: {}", imc, updatedPatient.getEtatImc());

        return patientMapper.fromEntity(updatedPatient);
    }

    private float calculateImc(float poids, float taille) {
        if (taille > 0) {
            return poids / (taille * taille);
        }
        throw new IllegalArgumentException("La taille doit être supérieure à zéro pour calculer l'IMC.");
    }

    private String determineImcState(float imc) {
        if (imc < 18.5) {
            return "Mince";
        } else if (imc < 24.9) {
            return "Normal";
        } else if (imc < 29.9) {
            return "Surpoids";
        } else {
            return "Obésité";
        }
    }


    public void activation(Map<String, String> activation) {
        log.debug("Request to activate user with code: {}", activation.get("code"));
        ValidationCodeDTO validationCodeDTO = validationCodeService.lireEnFonctionDuCode(activation.get("code"));
        log.debug("Validation code found:  {}", validationCodeDTO);

        if (Instant.now().isAfter(validationCodeDTO.getExpiration())) {
            throw new RuntimeException("Votre  code a expiré");
        }
        User userActiver = this.userRepository.findById(validationCodeDTO.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Utilisateur inconnu"));
        log.debug("User to activate found: {}", userActiver);

        userActiver.setActive(true);
        userRepository.save(userActiver);
        log.debug("User activated successfully: {}", userActiver);
    }


}