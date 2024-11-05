package com.esantefutur.esantefutur.service.Impl;

import com.esantefutur.esantefutur.models.UserRole;
import com.esantefutur.esantefutur.models.enums.RoleName;
import com.esantefutur.esantefutur.repositories.UserRoleRepository;
import com.esantefutur.esantefutur.service.RoleService;
import com.esantefutur.esantefutur.service.dto.RoleDTO;
import com.esantefutur.esantefutur.service.mappers.RoleMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final UserRoleRepository userRoleRepository;
    private final RoleMapper roleMapper;

    @Override
    public List<RoleDTO> getAllRoles() {
        return userRoleRepository.findAll().stream()
                .map(roleMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @PostConstruct
    public void initRoles() {
        List<RoleName> rolesToCreate = Arrays.asList(RoleName.PATIENT, RoleName.MEDECIN, RoleName.ADMINISTRATEUR);

        for (RoleName roleName : rolesToCreate) {
            if (!userRoleRepository.existsByLibelle(roleName)) {
                UserRole role = new UserRole();
                role.setLibelle(roleName);
                userRoleRepository.save(role);
            }
        }
    }
}
