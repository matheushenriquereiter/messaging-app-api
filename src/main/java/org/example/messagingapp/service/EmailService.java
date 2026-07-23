package org.example.messagingapp.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.example.messagingapp.exceptions.BusinessException;
import org.example.messagingapp.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final JwtService jwtService;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendVerificationEmail(User user) {
        String token = jwtService.generateVerificationToken(String.valueOf(user.getId()));
        String verifyUrl = "http://localhost:5173/auth/verify-email?jwtToken=" + URLEncoder.encode(token, StandardCharsets.UTF_8);

        String bodyHtml = "Click to verify your email: <a href='%s'>Verify Email</a>".formatted(verifyUrl);

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(user.getEmail());
            helper.setSubject("Verify Your Email");
            helper.setText(bodyHtml, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Failed to send verification email");
        }
    }
}
