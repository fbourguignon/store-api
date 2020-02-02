package com.store.unit.controller;

import com.google.gson.Gson;
import com.store.StoreApiApplication;
import com.store.config.SpringSecurityConfigTest;
import com.store.dto.RegisterRequestDTO;
import com.store.enumerator.RoleType;
import com.store.exception.StoreBusinessException;
import com.store.repository.RoleRepository;
import com.store.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = { SpringSecurityConfigTest.class, StoreApiApplication.class }
)
@AutoConfigureMockMvc
public class RegisterControllerTest {

    @MockBean
    private UserService userService;

    @Mock
    private RoleRepository roleRepository;

    @Autowired
    private MockMvc mvc;

    @Test
    public void mustRegisterUserWithSucess()
            throws Exception {

        mockRoleRepository();

        mvc.perform(post("/register")
                .content(mockJsonBody())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Usuário registrado com sucesso!")))
                .andExpect(status().isCreated());
    }

    @Test
    public void mustThrowExceptionWhenEmailAlreadyExists() throws Exception {

        doThrow(new StoreBusinessException("Email já cadastrado!")).when(userService).save(mockRegisterRequestDTO());

        mvc.perform(post("/register")
                .content(mockJsonBody())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Email já cadastrado!")))
                .andExpect(status().isUnprocessableEntity());
    }

    public String mockJsonBody(){
        Gson gson = new Gson();
        return gson.toJson(mockRegisterRequestDTO());
    }
    private void mockRoleRepository(){
        doReturn(Optional.of(RoleType.ROLE_USER)).when(roleRepository).findByType(RoleType.ROLE_USER);
    }

    private RegisterRequestDTO mockRegisterRequestDTO(){
        return RegisterRequestDTO
                .builder()
                .name("Jhon")
                .lastName("Whick")
                .mail("jhon@gmail.com")
                .password("123456")
                .build();
    }
}
