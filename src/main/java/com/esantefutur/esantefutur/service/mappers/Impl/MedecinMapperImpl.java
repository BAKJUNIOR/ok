package com.esantefutur.esantefutur.service.mappers.Impl;


import com.esantefutur.esantefutur.models.Medecin;
import com.esantefutur.esantefutur.service.dto.MedecinDTO;
import com.esantefutur.esantefutur.service.mappers.MedecinMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MedecinMapperImpl implements MedecinMapper {

    private final ModelMapper modelMapper;

    @Override
    public MedecinDTO fromEntity(Medecin entity) {
        return modelMapper.map(entity, MedecinDTO.class);
    }

    @Override
    public Medecin toEntity(MedecinDTO dto) {
        return modelMapper.map(dto, Medecin.class);
    }
}