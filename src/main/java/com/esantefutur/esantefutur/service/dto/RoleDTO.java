package com.esantefutur.esantefutur.service.dto;

import com.esantefutur.esantefutur.models.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private Long idRole;
    private RoleName libelle;
}