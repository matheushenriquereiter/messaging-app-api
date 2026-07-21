package org.example.messagingapp.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRegisterDTO(
        @NotBlank(message = "Username cannot be null or empty")
        String username,

        @NotBlank(message = "Email cannot be null or empty")
        String email,

        @NotBlank(message = "Password cannot be null or empty")
        String password
) {
}
