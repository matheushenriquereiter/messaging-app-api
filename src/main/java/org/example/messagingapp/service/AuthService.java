package org.example.messagingapp.service;

import org.example.messagingapp.dto.JwtTokenDTO;
import org.example.messagingapp.dto.UserLoginDTO;
import org.example.messagingapp.dto.UserRegisterDTO;
import org.example.messagingapp.exceptions.EmailAlreadyTakenException;
import org.example.messagingapp.exceptions.InvalidCredentialsException;
import org.example.messagingapp.model.User;
import org.example.messagingapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository, JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public void register(UserRegisterDTO userRegisterDTO) {
        Optional<User> user = userRepository.getUserByEmail(userRegisterDTO.email());

        if (user.isPresent()) {
            throw new EmailAlreadyTakenException();
        }

        String encodedPassword = passwordEncoder.encode(userRegisterDTO.password());

        userRepository.save(new User(userRegisterDTO.username(), userRegisterDTO.email(), encodedPassword));
    }

    public JwtTokenDTO login(UserLoginDTO userLoginDTO) {
        User user = userRepository.getUserByEmail(userLoginDTO.email()).orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(userLoginDTO.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return new JwtTokenDTO(jwtService.generateToken(user.getEmail()));
    }
}
