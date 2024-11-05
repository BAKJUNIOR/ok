package com.esantefutur.esantefutur.web.resource;


import com.esantefutur.esantefutur.service.PatientService;
import com.esantefutur.esantefutur.service.dto.PatientDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@Slf4j
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/register")
    public ResponseEntity<?> registerPatient(@Valid @RequestBody PatientDTO patientDTO) {
        log.debug("REST Request to register Patient: {}", patientDTO);
        try {
            PatientDTO registeredPatient = patientService.registerPatient(patientDTO);
            log.debug("Patient registered successfully: {}", registeredPatient);
            return new ResponseEntity<>(registeredPatient, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            log.error("Error registering patient: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/activation")
    public void activation(@RequestBody Map<String, String> activation) {
        log.debug("REST Request to activate Patient with activation data: {}", activation);
        this.patientService.activation(activation);
        log.debug("Patient activation completed successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id) {
        log.debug("REST Request to get Patient by ID: {}", id);
        PatientDTO patientDTO = patientService.getPatientById(id);
        log.debug("Patient found: {}", patientDTO);
        return ResponseEntity.ok(patientDTO);
    }

    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        log.debug("REST Request to get all Patients");
        List<PatientDTO> patients = patientService.getAllPatients();
        log.debug("Total Patients found: {}", patients.size());
        return ResponseEntity.ok(patients);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        log.debug("REST Request to delete Patient with ID: {}", id);
        patientService.deletePatient(id);
        log.debug("Patient deleted successfully: {}", id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateProfile")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody PatientDTO patientDTO) {
        log.debug("REST Request to update Patient profile: {}", patientDTO);
        try {
            PatientDTO updatedPatient = patientService.updateProfile(patientDTO);
            log.debug("Patient profile updated successfully: {}", updatedPatient);
            return ResponseEntity.ok(updatedPatient);
        } catch (RuntimeException e) {
            log.error("Error updating patient profile: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
