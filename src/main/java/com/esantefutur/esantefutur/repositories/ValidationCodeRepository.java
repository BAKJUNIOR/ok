package com.esantefutur.esantefutur.repositories;


import com.esantefutur.esantefutur.models.ValidationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValidationCodeRepository extends JpaRepository<ValidationCode, Integer> {
    Optional<ValidationCode> findByCode(String code);

    void deleteByUserId(Long id);
}
