package org.example.messagingapp.controller;

import org.example.messagingapp.dto.EmailDTO;
import org.example.messagingapp.service.EmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-mail")
    public String sendMail(@RequestBody EmailDTO emailDTO) {
        return emailService.sendSimpleMail(emailDTO);
    }
}
