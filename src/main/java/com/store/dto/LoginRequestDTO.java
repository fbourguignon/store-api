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
@Data public class LoginRequestDTO {

    @NotBlank
    @Email(message = "Informe um email válido!")
    private String username;

    @NotBlank
    private String password;

}
