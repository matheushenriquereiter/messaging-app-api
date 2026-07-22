package org.example.messagingapp.service;

import org.example.messagingapp.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final JwtService jwtService;

    @Value("${spring.mail.username}")
    private String sender;

    public EmailService(JavaMailSender mailSender, JwtService jwtService) {
        this.mailSender = mailSender;
        this.jwtService = jwtService;
    }

    public void sendVerificationEmail(User user) {
        String token = jwtService.generateVerificationToken(String.valueOf(user.getId()));
        String verifyUrl = "http://localhost:8080/auth/verify?jwtToken=" + URLEncoder.encode(token, StandardCharsets.UTF_8);

        String message = "Click below to verify your email:\n" + verifyUrl;

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setSubject("Verify your email");
        mail.setText(message);

        mailSender.send(mail);
    }
}
