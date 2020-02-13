package com.store.integration.controller;

import com.store.dto.LoginRequestDTO;

import com.store.dto.LoginResponseDTO;
import com.store.dto.ResponseDTO;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;



public class AuthControllerIntegrationTest extends AbstractControllerIntegrationTests{

    @Test
    public void mustLoginWithValidCredentials(){

        HttpEntity<LoginRequestDTO> request = new HttpEntity<>(simpleUserLoginRequest("123456"));
        ResponseEntity<LoginResponseDTO> response = restTemplate.exchange("/auth/login", HttpMethod.POST, request, LoginResponseDTO.class);
        LoginResponseDTO responseDTO = response.getBody();

        assertNotNull(responseDTO.getAccessToken());
        assertNotNull(responseDTO.getTokenType());
        assertNotNull(responseDTO.getId());

    }

    @Test
    public void mustNotLoginWithInvalidCredentials(){

        HttpEntity<LoginRequestDTO> request = new HttpEntity<>(simpleUserLoginRequest("1234567"));
        ResponseEntity<ResponseDTO> response = restTemplate.exchange("/auth/login", HttpMethod.POST, request, ResponseDTO.class);
        ResponseDTO responseDTO = response.getBody();

        assertEquals(responseDTO.getMessage(), "Usuário/Senha inválidos!");

    }

    protected LoginRequestDTO simpleUserLoginRequest(String password){
        return LoginRequestDTO
                .builder()
                .username("peter@gmail.com")
                .password(password)
                .build();
    }


}
