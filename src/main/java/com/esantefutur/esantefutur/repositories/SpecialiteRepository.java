package com.esantefutur.esantefutur.repositories;


import com.esantefutur.esantefutur.models.Specialite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SpecialiteRepository extends JpaRepository<Specialite, Long> {
    Optional<Specialite> findByNom(String key);
}