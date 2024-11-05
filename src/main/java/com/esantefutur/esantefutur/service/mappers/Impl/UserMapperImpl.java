package com.esantefutur.esantefutur.service.mappers.Impl;

import com.esantefutur.esantefutur.models.User;
import com.esantefutur.esantefutur.service.dto.UserDTO;
import com.esantefutur.esantefutur.service.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {

    private final ModelMapper modelMapper;

    @Override
    public UserDTO fromEntity(User entity) {
        return modelMapper.map(entity, UserDTO.class);
    }

    @Override
    public User toEntity(UserDTO dto) {
        return modelMapper.map(dto, User.class);
    }
}