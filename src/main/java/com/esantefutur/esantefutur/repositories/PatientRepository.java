package com.esantefutur.esantefutur.repositories;



import com.esantefutur.esantefutur.models.Patient;
import com.esantefutur.esantefutur.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByUser_Id(Long userId);

}