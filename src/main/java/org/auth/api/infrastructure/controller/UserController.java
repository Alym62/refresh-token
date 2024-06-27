package org.auth.api.infrastructure.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
    @GetMapping
    public ResponseEntity<String> fetchAll() {
        return ResponseEntity.ok().body("Token validado");
    }
}
