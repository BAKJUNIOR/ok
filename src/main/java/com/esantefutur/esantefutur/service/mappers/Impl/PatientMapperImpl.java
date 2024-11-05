package com.esantefutur.esantefutur.service.mappers.Impl;


import com.esantefutur.esantefutur.models.Patient;
import com.esantefutur.esantefutur.service.dto.PatientDTO;
import com.esantefutur.esantefutur.service.mappers.PatientMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatientMapperImpl implements PatientMapper {

    private final ModelMapper modelMapper;

    @Override
    public PatientDTO fromEntity(Patient entity) {
        return modelMapper.map(entity, PatientDTO.class);
    }

    @Override
    public Patient toEntity(PatientDTO dto) {
        return modelMapper.map(dto, Patient.class);
    }
}