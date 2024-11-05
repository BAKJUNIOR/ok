package com.esantefutur.esantefutur.repositories;


import com.esantefutur.esantefutur.models.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    Optional<Medecin> findByUser_Id(Long userId);


}
