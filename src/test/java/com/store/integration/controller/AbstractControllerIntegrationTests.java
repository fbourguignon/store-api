package com.store.integration.controller;

import com.store.dto.LoginRequestDTO;
import com.store.dto.LoginResponseDTO;
import com.store.dto.RegisterRequestDTO;
import com.store.enumerator.RoleType;
import com.store.model.Role;
import com.store.repository.RoleRepository;
import com.store.repository.UserRepository;
import com.store.service.UserService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public abstract class AbstractControllerIntegrationTests {

    @Before
    public void before(){
        persistRoles();
        persistAdminUser();
        persistSimpleUser();
    }


    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    protected void persistSimpleUser(){

        userService.createUser(RegisterRequestDTO.builder()
                .name("User")
                .lastName("Test")
                .mail("user@gmail.com")
                .password("123456")
                .build());
    }

    protected void persistAdminUser(){

        userService.createAdminUser(RegisterRequestDTO.builder()
                .name("User")
                .lastName("Test")
                .mail("admin@gmail.com")
                .password("123456")
                .build());
    }

    protected void persistRoles(){
        roleRepository.save(Role.builder().type(RoleType.ROLE_USER).build());
        roleRepository.save(Role.builder().type(RoleType.ROLE_ADMIN).build());
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
