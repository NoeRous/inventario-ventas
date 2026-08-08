package com.divinamoda.inventary.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.divinamoda.inventary.dto.auth.LoginRequest;
import com.divinamoda.inventary.dto.auth.LoginResponse;
import com.divinamoda.inventary.dto.auth.RegisterRequest;
import com.divinamoda.inventary.entity.auth.User;
import com.divinamoda.inventary.enums.Role;
import com.divinamoda.inventary.exception.BadRequestException;
import com.divinamoda.inventary.repository.UserRepository;
import com.divinamoda.inventary.security.JwtService;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Credenciales inválidas"));

        return toLoginResponse(user);
    }

    public LoginResponse register(RegisterRequest request) {
        if (request.getFullName() == null || request.getFullName().isBlank()) {
            throw new BadRequestException("El nombre completo es obligatorio");
        }
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new BadRequestException("El email es obligatorio");
        }
        if (request.getPassword() == null || request.getPassword().length() < 6) {
            throw new BadRequestException("La contraseña debe tener al menos 6 caracteres");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("El email ya está registrado");
        }

        Role role;
        try {
            role = Role.valueOf(request.getRole());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Rol inválido: debe ser ADMIN o EMPLOYEE");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        user.setActive(true);

        User saved = userRepository.save(user);
        return toLoginResponse(saved);
    }

    private LoginResponse toLoginResponse(User user) {
        return new LoginResponse(
                jwtService.generateToken(user),
                user.getEmail(),
                user.getFullName(),
                user.getRole().name());
    }
}
