package com.store.controller;

import com.store.dto.RegisterRequestDTO;
import com.store.dto.ResponseDTO;
import com.store.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
public class RegisterController {

    private UserService userService;

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequestDTO createRegistrationRequest) {
        userService.save(createRegistrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("Usuário registrado com sucesso!"));
    }
}
