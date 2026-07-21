package org.example.messagingapp.service;

import org.example.messagingapp.dto.EmailDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public String sendSimpleMail(EmailDTO emailDTO) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(emailDTO.recipient());
            mailMessage.setText(emailDTO.msgBody());
            mailMessage.setSubject(emailDTO.subject());

            javaMailSender.send(mailMessage);

            return  "Mail sent successfully";
        } catch (Exception e) {
            return  "Error while sending mail";
        }
    }
}
