package com.esantefutur.esantefutur.service.mappers.Impl;

import com.esantefutur.esantefutur.models.UserRole;
import com.esantefutur.esantefutur.service.dto.RoleDTO;
import com.esantefutur.esantefutur.service.mappers.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleMapperImpl implements RoleMapper {

    private final ModelMapper modelMapper;

    @Override
    public RoleDTO fromEntity(UserRole entity) {
        return modelMapper.map(entity, RoleDTO.class);
    }

    @Override
    public UserRole toEntity(RoleDTO dto) {
        return modelMapper.map(dto, UserRole.class);
    }
}