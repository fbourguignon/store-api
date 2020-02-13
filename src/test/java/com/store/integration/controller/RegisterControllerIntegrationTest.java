package com.store.integration.controller;


import com.store.dto.RegisterRequestDTO;
import com.store.dto.ResponseDTO;
import com.store.model.User;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;


public class RegisterControllerIntegrationTest extends AbstractControllerIntegrationTests {

    @Test
    public void registerUsingNewEmailWithSucess() {

        RegisterRequestDTO registerRequest = RegisterRequestDTO
                .builder()
                .name("User")
                .lastName("Test")
                .mail("newUser@gmail.com")
                .password("123456")
                .build();


        HttpEntity<RegisterRequestDTO> request = new HttpEntity<>(registerRequest);
        ResponseEntity<ResponseDTO> response = restTemplate.exchange("/register", HttpMethod.POST, request, ResponseDTO.class);
        ResponseDTO responseDTO = response.getBody();

        User user = userRepository.findByMail(registerRequest.getMail()).get();
        assertEquals(registerRequest.getMail(), user.getMail());
        assertEquals(responseDTO.getMessage(),"Usuário registrado com sucesso!");
    }

    @Test
    public void throwsExceptionWithAlreadyRegisteredEmail() throws Exception {

        RegisterRequestDTO registerRequest = RegisterRequestDTO
                .builder()
                .name("User")
                .lastName("Test")
                .mail("peter@gmail.com")
                .password("123456")
                .build();


        HttpEntity<RegisterRequestDTO> request = new HttpEntity<>(registerRequest);
        ResponseEntity<ResponseDTO> response = restTemplate.exchange("/register", HttpMethod.POST, request, ResponseDTO.class);
        ResponseDTO responseDTO = response.getBody();

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY,response.getStatusCode());
        assertEquals(responseDTO.getMessage(),"Email já cadastrado!");


    }
}
