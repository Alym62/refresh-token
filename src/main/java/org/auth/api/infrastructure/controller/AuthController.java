package org.auth.api.infrastructure.controller;

import org.auth.api.application.services.JwtService;
import org.auth.api.application.services.UserService;
import org.auth.api.infrastructure.payload.JWTTokenRequestDTO;
import org.auth.api.infrastructure.payload.JWTTokenResponseDTO;
import org.auth.api.infrastructure.payload.UserRecordDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Value("${auth.jwt.token.expires}")
    private Integer EXPIRES_IN;

    @Value("${auth.jwt.refresh-token}")
    private Integer EXPIRES_IN_REFRESH;

    private final JwtService jwtService;
    private final UserService service;
    private final AuthenticationManager manager;

    public AuthController(JwtService jwtService, UserService service, AuthenticationManager manager) {
        this.jwtService = jwtService;
        this.service = service;
        this.manager = manager;
    }

    @PostMapping("/create")
    public ResponseEntity<UserRecordDTO> create(@RequestBody UserRecordDTO dto) {
        var user = service.create(dto);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<JWTTokenResponseDTO> login(@RequestBody UserRecordDTO dto) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(dto.username(), dto.password()));

        var user = service.loadUserByUsername(dto.username());

        return ResponseEntity
                .ok()
                .body(
                        JWTTokenResponseDTO
                                .builder()
                                .token(jwtService.generateToken(user, EXPIRES_IN))
                                .RefreshToken(jwtService.generateToken(user, EXPIRES_IN_REFRESH))
                                .build()
                );
    }

    @PostMapping("/refresh")
    public ResponseEntity<JWTTokenResponseDTO> refreshToken(@RequestBody JWTTokenRequestDTO dto) {
        var username = jwtService.extractUser(dto.refreshToken());
        var user = service.loadUserByUsername(username);

        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null,
                user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        return ResponseEntity
                .ok()
                .body(
                        JWTTokenResponseDTO
                                .builder()
                                .token(jwtService.generateToken(user, EXPIRES_IN))
                                .RefreshToken(jwtService.generateToken(user, EXPIRES_IN_REFRESH))
                                .build()
                );
    }
}
