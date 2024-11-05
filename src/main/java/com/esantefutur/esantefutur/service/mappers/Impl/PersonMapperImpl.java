package com.esantefutur.esantefutur.service.mappers.Impl;


import com.esantefutur.esantefutur.models.Patient;
import com.esantefutur.esantefutur.models.Person;
import com.esantefutur.esantefutur.service.dto.PatientDTO;
import com.esantefutur.esantefutur.service.dto.PersonDTO;
import com.esantefutur.esantefutur.service.mappers.PatientMapper;
import com.esantefutur.esantefutur.service.mappers.PersonMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonMapperImpl implements PersonMapper {

    private final ModelMapper modelMapper;

    @Override
    public PersonDTO fromEntity(Person entity) {
        return modelMapper.map(entity, PersonDTO.class);
    }

    @Override
    public Person toEntity(PersonDTO dto) {
        return modelMapper.map(dto, Person.class);
    }
}