package com.store.integration;

import com.store.dto.LoginRequestDTO;
import com.store.dto.ResponseDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void mustNotLoginWithInvalidCredentials(){

        LoginRequestDTO loginRequestDTO = LoginRequestDTO
                .builder()
                .username("user@gmail.com")
                .password("123456")
                .build();

        HttpEntity<LoginRequestDTO> request = new HttpEntity<>(loginRequestDTO);
        ResponseEntity<ResponseDTO> response = restTemplate.exchange("/auth/login", HttpMethod.POST, request, ResponseDTO.class);
        ResponseDTO responseDTO = response.getBody();

        assertEquals(responseDTO.getMessage(), "Usuário/Senha inválidos!");

    }
}
