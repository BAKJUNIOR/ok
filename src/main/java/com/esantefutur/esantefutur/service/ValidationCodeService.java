package com.esantefutur.esantefutur.service;


import com.esantefutur.esantefutur.service.dto.UserDTO;
import com.esantefutur.esantefutur.service.dto.ValidationCodeDTO;

public interface ValidationCodeService {

    void enregistrerCode(UserDTO userDTO);
     ValidationCodeDTO lireEnFonctionDuCode(String code);

}