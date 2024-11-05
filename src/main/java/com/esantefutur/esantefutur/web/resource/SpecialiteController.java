package com.esantefutur.esantefutur.web.resource;

import com.esantefutur.esantefutur.service.SpecialiteService;
import com.esantefutur.esantefutur.service.dto.MedecinDTO;
import com.esantefutur.esantefutur.service.dto.SpecialiteDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/specialites")
public class SpecialiteController {

    private final SpecialiteService specialiteService;

    @PostMapping
    public ResponseEntity<SpecialiteDTO> createSpecialite(@RequestBody SpecialiteDTO specialiteDTO) {
        log.debug("Request to create Specialite: {}", specialiteDTO);
        SpecialiteDTO result = specialiteService.createSpecialite(specialiteDTO);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecialiteDTO> updateSpecialite(@PathVariable Long id, @RequestBody SpecialiteDTO specialiteDTO) {
        log.debug("Request to update Specialite with id {}: {}", id, specialiteDTO);
        SpecialiteDTO result = specialiteService.updateSpecialite(id, specialiteDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialiteDTO> getSpecialiteById(@PathVariable Long id) {
        log.debug("Request to get Specialite with id: {}", id);
        SpecialiteDTO specialiteDTO = specialiteService.getSpecialiteById(id);
        log.debug("specialite found: {}", specialiteDTO);
        return ResponseEntity.ok(specialiteDTO);
    }

    @GetMapping
    public ResponseEntity<List<SpecialiteDTO>> getAllSpecialites() {
        log.debug("Request to get all Specialites");
        List<SpecialiteDTO> result = specialiteService.getAllSpecialites();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialite(@PathVariable Long id) {
        log.debug("Request to delete Specialite with id: {}", id);
        specialiteService.deleteSpecialite(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{specialiteId}/medecins")
    public ResponseEntity<List<MedecinDTO>> getMedecinsBySpecialite(@PathVariable Long specialiteId) {
        log.debug("Request to get Medecins by Specialite with id: {}", specialiteId);
        List<MedecinDTO> medecins = specialiteService.getMedecinsBySpecialite(specialiteId);
        return ResponseEntity.ok(medecins);
    }
}
