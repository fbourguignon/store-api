package com.store.controller;

import com.store.dto.LoginRequestDTO;
import com.store.dto.LoginResponseDTO;
import com.store.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Api(value = "Auth", description = "Actions de autenticação")
public class AuthController {


    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @ApiOperation(value = "Autentica um usuário na aplicação")
    public ResponseEntity<LoginResponseDTO> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
            return ResponseEntity.ok(authService.login(loginRequest));
    }
}
