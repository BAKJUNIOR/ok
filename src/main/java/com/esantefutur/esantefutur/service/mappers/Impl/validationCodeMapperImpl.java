package com.esantefutur.esantefutur.service.mappers.Impl;


import com.esantefutur.esantefutur.models.ValidationCode;
import com.esantefutur.esantefutur.service.dto.ValidationCodeDTO;
import com.esantefutur.esantefutur.service.mappers.validationCodeMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class validationCodeMapperImpl implements validationCodeMapper {
    private final ModelMapper modelMapper;


    @Override
    public ValidationCodeDTO fromEntity(ValidationCode entity) {
        return modelMapper.map(entity, ValidationCodeDTO.class);
    }

    @Override
    public ValidationCode toEntity(ValidationCodeDTO dto) {
        return modelMapper.map(dto, ValidationCode.class);
    }


}
