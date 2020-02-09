package com.store.integration.controller;

import com.store.dto.LoginRequestDTO;
import com.store.dto.LoginResponseDTO;
import com.store.dto.RegisterRequestDTO;
import com.store.enumerator.RoleType;
import com.store.model.Role;
import com.store.repository.RoleRepository;
import com.store.service.UserService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
public abstract class BaseIntegrationTestController {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    protected void persistSimpleUser(){
        roleRepository.save(Role.builder().type(RoleType.ROLE_USER).build());

        userService.createUser(RegisterRequestDTO.builder()
                .name("User")
                .lastName("Test")
                .mail("user@gmail.com")
                .password("123456")
                .build());
    }

    protected void persistAdminUser(){
        roleRepository.save(Role.builder().type(RoleType.ROLE_ADMIN).build());

        userService.createAdminUser(RegisterRequestDTO.builder()
                .name("User")
                .lastName("Test")
                .mail("admin@gmail.com")
                .password("123456")
                .build());
    }

    protected LoginRequestDTO simpleUserLoginRequest(String password){
        return LoginRequestDTO
                .builder()
                .username("user@gmail.com")
                .password(password)
                .build();
    }

    protected LoginRequestDTO adminUserLoginRequest(String password){
        return LoginRequestDTO
                .builder()
                .username("admin@gmail.com")
                .password(password)
                .build();
    }

    protected String getJWTAdmin(){

            HttpEntity<LoginRequestDTO> request = new HttpEntity<>(adminUserLoginRequest("123456"));
            ResponseEntity<LoginResponseDTO> response = restTemplate.exchange("/auth/login", HttpMethod.POST, request, LoginResponseDTO.class);
            LoginResponseDTO responseDTO = response.getBody();

            return responseDTO.getTokenType()+ " " + responseDTO.getAccessToken();
    }
}
