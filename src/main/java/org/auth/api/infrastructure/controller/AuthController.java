package org.auth.api.infrastructure.controller;

import lombok.AllArgsConstructor;
import org.auth.api.application.services.JwtService;
import org.auth.api.application.services.UserService;
import org.auth.api.infrastructure.payload.UserRecordDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final JwtService jwtService;
    private final UserService service;
    private final AuthenticationManager manager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRecordDTO dto) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(dto.username(), dto.password()));

        var user = service.loadUserByUsername(dto.username());

        return ResponseEntity.ok().body(jwtService.generateToken(user));
    }
}
