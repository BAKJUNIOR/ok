package com.esantefutur.esantefutur.web.resource;


import com.esantefutur.esantefutur.service.MedecinService;
import com.esantefutur.esantefutur.service.dto.MedecinDTO;
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
@RequestMapping("/api/medecins")
@RequiredArgsConstructor
@Slf4j
public class MedecinController {

    private final MedecinService medecinService;



    @PostMapping("/register")
    public ResponseEntity<?> registerMedecin(@Valid @RequestBody MedecinDTO medecinDTO) {
        log.debug("REST Request to register Medecin: {}", medecinDTO);
        try {
            MedecinDTO registeredMedecin = medecinService.registerMedecin(medecinDTO);
            log.debug("Medecin registered successfully: {}", registeredMedecin);
            return new ResponseEntity<>(registeredMedecin, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            log.error("Error registering patient: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/activation")
    public void activation(@RequestBody Map<String, String> activation) {
        log.debug("REST Request to activate Patient with activation data: {}", activation);
        this.medecinService.activation(activation);
        log.debug("Patient activation completed successfully");
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getMedecinById(@PathVariable Long id) {
        log.debug("REST Request to get Medecin by ID: {}", id);
        try {
            MedecinDTO medecinDTO = medecinService.getAMedecinById(id);
            log.debug("Medecin found: {}", medecinDTO);
            return ResponseEntity.ok(medecinDTO);
        } catch (RuntimeException e) {
            log.error("Error retrieving Medecin by ID: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Médecin non trouvé avec ID: " + id);
        }
    }

    @GetMapping
    public ResponseEntity<List<MedecinDTO>> getAllMedecins() {
        log.debug("REST Request to get all Medecins");
        List<MedecinDTO> medecins = medecinService.getAllMedecin();
        log.debug("Total Medecins found: {}", medecins.size());
        return ResponseEntity.ok(medecins);
    }

//    @GetMapping("/specialite/{specialiteId}")
//    public ResponseEntity<?> getMedecinsBySpecialite(@PathVariable Long specialiteId) {
//        log.debug("REST Request to get Medecins by Specialite ID: {}", specialiteId);
//        try {
//            List<MedecinDTO> medecinDTOs = medecinService.findMedecinsBySpecialite(specialiteId);
//            if (medecinDTOs.isEmpty()) {
//                log.warn("No Medecins found for Specialite ID: {}", specialiteId);
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body("Aucun médecin trouvé pour la spécialité avec ID: " + specialiteId);
//            }
//            return ResponseEntity.ok(medecinDTOs);
//        } catch (RuntimeException e) {
//            log.error("Error retrieving Medecins by Specialite: {}", e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMedecin(@PathVariable Long id) {
        log.debug("REST Request to delete Medecin with ID: {}", id);
        try {
            medecinService.deleteMedecin(id);
            log.debug("Medecin deleted successfully: {}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Error deleting Medecin with ID: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Médecin non trouvé avec ID: " + id);
        }
    }
}
