package org.auth.api.infrastructure.controller;

import lombok.AllArgsConstructor;
import org.auth.api.application.services.UserService;
import org.auth.api.infrastructure.payload.UserRecordDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping("/create")
    public ResponseEntity<UserRecordDTO> create(@RequestBody UserRecordDTO dto) {
        var user = service.create(dto);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping
    public ResponseEntity<String> fetchAll() {
        return ResponseEntity.ok().body("Token validado");
    }
}
