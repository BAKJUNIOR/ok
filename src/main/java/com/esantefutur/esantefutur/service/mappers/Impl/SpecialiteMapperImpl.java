package com.esantefutur.esantefutur.service.mappers.Impl;


import com.esantefutur.esantefutur.models.Person;
import com.esantefutur.esantefutur.models.Specialite;
import com.esantefutur.esantefutur.service.dto.PersonDTO;
import com.esantefutur.esantefutur.service.dto.SpecialiteDTO;
import com.esantefutur.esantefutur.service.mappers.PersonMapper;
import com.esantefutur.esantefutur.service.mappers.SpecialiteMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpecialiteMapperImpl implements SpecialiteMapper {

    private final ModelMapper modelMapper;

    @Override
    public SpecialiteDTO fromEntity(Specialite entity) {
        return modelMapper.map(entity, SpecialiteDTO.class);
    }

    @Override
    public Specialite toEntity(SpecialiteDTO dto) {
        return modelMapper.map(dto, Specialite.class);
    }
}