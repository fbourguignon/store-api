package com.store.controller;

import com.store.dto.RegisterRequestDTO;
import com.store.dto.ResponseDTO;
import com.store.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
@Api(value = "Register", description = "Action de cadastro do usuário")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping
    @ApiOperation(value = "Cadastra um usuário na aplicação")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequestDTO createRegistrationRequest) {
        userService.createUser(createRegistrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("Usuário registrado com sucesso!"));
    }
}
