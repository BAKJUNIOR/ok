package com.esantefutur.esantefutur.service;



import com.esantefutur.esantefutur.models.Patient;
import com.esantefutur.esantefutur.service.dto.PatientDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PatientService {
    PatientDTO getPatientById(Long id);
    Optional<Patient> getPatientByUserId(Long userId);
    List<PatientDTO> getAllPatients();
    void deletePatient(Long id);

    PatientDTO registerPatient(PatientDTO patientDTO);
    void activation(Map<String, String> activation);

    PatientDTO updateProfile(PatientDTO patientDTO);
}