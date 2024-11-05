package com.esantefutur.esantefutur.service.Impl;


import com.esantefutur.esantefutur.service.NotificationCodeService;
import com.esantefutur.esantefutur.service.dto.ValidationCodeDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;



@Service
@RequiredArgsConstructor
public class NotificationCodeServiceImpl implements NotificationCodeService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void envoyerCode(ValidationCodeDTO validationCodeDTO) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom("bakbassjunior@gmail.com");
            helper.setTo(validationCodeDTO.getUser().getEmail());
            helper.setSubject("Votre code d'activation");

            Context context = new Context();
            context.setVariable("username", validationCodeDTO.getUser().getUsername());
            context.setVariable("code", validationCodeDTO.getCode());

            String htmlContent = templateEngine.process("activation-email", context);
            helper.setText(htmlContent, true);

            helper.addInline("logoImage", new ClassPathResource("/static/images/logo.png"));

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'email", e);
        }
    }
}
