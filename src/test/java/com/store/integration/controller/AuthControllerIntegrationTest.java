package com.store.integration.controller;

import com.google.gson.Gson;
import com.store.dto.LoginRequestDTO;

import org.junit.Test;


import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AuthControllerIntegrationTest extends AbstractControllerIntegrationTests{

    @Test
    public void mustLoginWithValidCredentials() throws Exception {

        Gson gson = new Gson();
        String json = gson.toJson(simpleUserLoginRequest("123456"));

        mockMvc.perform(post("/auth/login")
                .contentType("application/json")
                .content(json))
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.tokenType").exists())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void mustNotLoginWithInvalidCredentials() throws Exception {

        Gson gson = new Gson();
        String json = gson.toJson(simpleUserLoginRequest("1234567"));

        mockMvc.perform(post("/auth/login")
                .contentType("application/json")
                .content(json))
                .andExpect(jsonPath("$.message", is("Usuário/Senha inválidos!")))
                .andExpect(status().isBadRequest());

    }

    protected LoginRequestDTO simpleUserLoginRequest(String password){
        return LoginRequestDTO
                .builder()
                .username("user@gmail.com")
                .password(password)
                .build();
    }


}
