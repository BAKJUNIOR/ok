package com.esantefutur.esantefutur.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationCodeDTO {
    private Long id;
    private Instant creation;
    private Instant expiration;
    private String code;
    private UserDTO user;
}
