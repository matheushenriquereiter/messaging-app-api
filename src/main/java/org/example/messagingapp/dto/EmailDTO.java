package org.example.messagingapp.dto;

public record EmailDTO(
        String recipient,
        String msgBody,
        String subject
) {
}
