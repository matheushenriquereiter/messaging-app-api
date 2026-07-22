package org.example.messagingapp.dto;

import jakarta.validation.constraints.NotBlank;

public record MessageSendDTO(
        @NotBlank(message = "Message content field cannot be null or empty")
        String content
) {
}
