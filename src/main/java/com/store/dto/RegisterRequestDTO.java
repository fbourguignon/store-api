package com.store.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class RegisterRequestDTO {

    @NotBlank(message = "Informe o nome")
    private String name;

    @NotBlank(message = "Informe o sobrenome")
    private String lastName;

    @NotBlank(message = "Informe o email")
    @Email(message = "Informe um email válido")
    private String mail;

    @NotBlank(message = "Informe a senha")
    private String password;
}
