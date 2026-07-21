package org.example.messagingapp.dto;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDTO(
        @NotBlank(message = "Email cannot be null or empty")
        String email,

        @NotBlank(message = "Password cannot be null or empty")
        String password
) {
}
