package org.example.messagingapp.controller;

import jakarta.validation.Valid;
import org.example.messagingapp.dto.JwtTokenDTO;
import org.example.messagingapp.dto.UserLoginDTO;
import org.example.messagingapp.dto.UserRegisterDTO;
import org.example.messagingapp.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        authService.register(userRegisterDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/login")
    public ResponseEntity<JwtTokenDTO> register(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        JwtTokenDTO jwtTokenDTO = authService.login(userLoginDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(jwtTokenDTO);
    }
}
