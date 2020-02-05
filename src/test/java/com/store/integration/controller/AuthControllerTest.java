package com.store.integration.controller;

import com.store.dto.LoginRequestDTO;
import com.store.dto.LoginResponseDTO;
import com.store.dto.RegisterRequestDTO;
import com.store.dto.ResponseDTO;
import com.store.enumerator.RoleType;
import com.store.model.Role;
import com.store.repository.RoleRepository;
import com.store.service.UserService;
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
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;



    @Test
    public void mustLoginWithValidCredentials(){

        mockPersistedData();

        HttpEntity<LoginRequestDTO> request = new HttpEntity<>(mockLoginRequest("123456"));
        ResponseEntity<LoginResponseDTO> response = restTemplate.exchange("/auth/login", HttpMethod.POST, request, LoginResponseDTO.class);
        LoginResponseDTO responseDTO = response.getBody();

        assertNotNull(responseDTO.getAccessToken());
        assertNotNull(responseDTO.getTokenType());
        assertNotNull(responseDTO.getId());

    }

    @Test
    public void mustNotLoginWithInvalidCredentials(){

        HttpEntity<LoginRequestDTO> request = new HttpEntity<>(mockLoginRequest("1234567"));
        ResponseEntity<ResponseDTO> response = restTemplate.exchange("/auth/login", HttpMethod.POST, request, ResponseDTO.class);
        ResponseDTO responseDTO = response.getBody();

        assertEquals(responseDTO.getMessage(), "Usuário/Senha inválidos!");

    }

    private LoginRequestDTO mockLoginRequest(String password){
        return LoginRequestDTO
                .builder()
                .username("user@gmail.com")
                .password(password)
                .build();
    }

    private void mockPersistedData(){
        roleRepository.save(Role.builder().type(RoleType.ROLE_USER).build());

        userService.save(RegisterRequestDTO.builder()
                .name("User")
                .lastName("Test")
                .mail("user@gmail.com")
                .password("123456")
                .build());
    }
}
