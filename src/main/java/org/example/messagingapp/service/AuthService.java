package org.example.messagingapp.service;

import lombok.RequiredArgsConstructor;
import org.example.messagingapp.dto.JwtTokenDTO;
import org.example.messagingapp.dto.UserLoginDTO;
import org.example.messagingapp.dto.UserRegisterDTO;
import org.example.messagingapp.exceptions.BusinessException;
import org.example.messagingapp.model.User;
import org.example.messagingapp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final EmailService emailService;

    public void register(UserRegisterDTO userRegisterDTO) {
        Optional<User> user = userRepository.getUserByEmail(userRegisterDTO.email());

        if (user.isPresent()) {
            throw new BusinessException(HttpStatus.CONFLICT, "Email already taken");
        }

        String encodedPassword = passwordEncoder.encode(userRegisterDTO.password());
        User userToRegister = new User(userRegisterDTO.username(), userRegisterDTO.email(), encodedPassword);

        userRepository.save(userToRegister);

        emailService.sendVerificationEmail(userToRegister);
    }

    public void verifyEmail(String jwtToken) {
        if (!jwtService.isTokenValid(jwtToken)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Invalid or expired token");
        }

        String userId = jwtService.extractSubject(jwtToken);
        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "User not found"));

        if (user.isVerified()) {
            throw new BusinessException(HttpStatus.CONFLICT, "User already verified");
        }

        user.setVerified(true);
        userRepository.save(user);
    }

    public JwtTokenDTO login(UserLoginDTO userLoginDTO) {
        User user = userRepository.getUserByEmail(userLoginDTO.email()).orElseThrow(() -> new BusinessException(HttpStatus.BAD_REQUEST, "Invalid credentials"));

        if (!passwordEncoder.matches(userLoginDTO.password(), user.getPassword())) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Invalid credentials");
        }

        if (!user.isVerified()) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED, "User not verified");
        }

        return new JwtTokenDTO(jwtService.generateToken(user.getEmail()));
    }
}
