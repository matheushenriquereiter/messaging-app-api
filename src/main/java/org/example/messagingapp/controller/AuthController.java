package org.example.messagingapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.messagingapp.dto.JwtTokenDTO;
import org.example.messagingapp.dto.UserLoginDTO;
import org.example.messagingapp.dto.UserRegisterDTO;
import org.example.messagingapp.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        authService.register(userRegisterDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/validate-email")
    public ResponseEntity<Void> validateEmail(@Valid @RequestBody JwtTokenDTO validationTokenDTO) {
        authService.validateEmail(validationTokenDTO.jwtToken());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDTO> register(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        JwtTokenDTO jwtTokenDTO = authService.login(userLoginDTO);

        return ResponseEntity.ok(jwtTokenDTO);
    }
}
