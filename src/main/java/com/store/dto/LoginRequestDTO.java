package com.store.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data public class LoginRequestDTO {

    @NotBlank
    @Email(message = "Informe um email válido!")
    private String username;

    @NotBlank
    private String password;

}
