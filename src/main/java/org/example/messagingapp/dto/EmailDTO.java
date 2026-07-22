package org.example.messagingapp.dto;

import jakarta.validation.constraints.NotBlank;

public record EmailDTO(
        @NotBlank(message = "Email recipient cannot be null or empty")
        String recipient,

        @NotBlank(message = "Email body cannot be null or empty")
        String body,

        @NotBlank(message = "Email subject cannot be null or empty")
        String subject
) {
}
