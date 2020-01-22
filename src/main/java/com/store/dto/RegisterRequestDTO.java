package com.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@NoArgsConstructor
@AllArgsConstructor
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
