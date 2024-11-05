package com.esantefutur.esantefutur.service.dto;

import com.esantefutur.esantefutur.models.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {

    private Long idPerson;
    private String firstName;
    private String lastName;
    private String numbers;
    private LocalDate dateOfBirth;
    private String photoProfil;
    private String slug;
    private Gender gender;
    private String adresse;
    private UserDTO user;
}
