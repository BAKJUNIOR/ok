package com.esantefutur.esantefutur.service;


import com.esantefutur.esantefutur.service.dto.ValidationCodeDTO;

public interface NotificationCodeService {
    void envoyerCode(ValidationCodeDTO validationCodeDTO);
}