package com.esantefutur.esantefutur.repositories;

import com.esantefutur.esantefutur.models.UserRole;
import com.esantefutur.esantefutur.models.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByLibelle(RoleName roleName);
    boolean existsByLibelle(RoleName roleName);
}