package com.esantefutur.esantefutur.service.Impl;

;
import com.esantefutur.esantefutur.models.User;
import com.esantefutur.esantefutur.models.ValidationCode;
import com.esantefutur.esantefutur.repositories.UserRepository;
import com.esantefutur.esantefutur.repositories.ValidationCodeRepository;
import com.esantefutur.esantefutur.service.NotificationCodeService;
import com.esantefutur.esantefutur.service.ValidationCodeService;
import com.esantefutur.esantefutur.service.dto.UserDTO;
import com.esantefutur.esantefutur.service.dto.ValidationCodeDTO;
import com.esantefutur.esantefutur.service.mappers.UserMapper;
import com.esantefutur.esantefutur.service.mappers.validationCodeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
@RequiredArgsConstructor
public class ValidationCodeServiceImpl implements ValidationCodeService {

    private final ValidationCodeRepository validationCodeRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final NotificationCodeService notificationCodeService;
    private final validationCodeMapper validationCodeMapper;

    @Override
    public void enregistrerCode(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        ValidationCodeDTO validationCodeDTO = new ValidationCodeDTO();
        validationCodeDTO.setUser(userMapper.fromEntity(user));
        Instant creation = Instant.now();
        validationCodeDTO.setCreation(creation);
        Instant expiration = creation.plus(10, MINUTES);
        validationCodeDTO.setExpiration(expiration);

        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);
        validationCodeDTO.setCode(code);

        ValidationCode validationCodeEntity = validationCodeMapper.toEntity(validationCodeDTO);

        validationCodeRepository.save(validationCodeEntity);
        notificationCodeService.envoyerCode(validationCodeDTO);
    }

    @Override
    public ValidationCodeDTO lireEnFonctionDuCode(String code) {
        ValidationCode validationCode = this.validationCodeRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Votre code est invalide"));
        return validationCodeMapper.fromEntity(validationCode);
    }

}
