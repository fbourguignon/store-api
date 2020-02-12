package com.store.integration.controller;

import com.google.gson.Gson;
import com.store.dto.RegisterRequestDTO;
import com.store.model.User;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegisterControllerIntegrationTest extends AbstractControllerIntegrationTests {

    @Test
    public void registerUsingNewEmailWithSucess() throws Exception {

        RegisterRequestDTO registerRequest = RegisterRequestDTO
                .builder()
                .name("User")
                .lastName("Test")
                .mail("newUser@gmail.com")
                .password("123456")
                .build();

        Gson gson = new Gson();
        String json = gson.toJson(registerRequest);

        mockMvc.perform(post("/register")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isCreated());

        User user = userRepository.findByMail(registerRequest.getMail()).get();
        assertEquals(registerRequest.getMail(), user.getMail());
    }

    @Test
    public void throwsExceptionWithAlreadyRegisteredEmailInAllLayers() throws Exception {

        RegisterRequestDTO registerRequest = RegisterRequestDTO
                .builder()
                .name("User")
                .lastName("Test")
                .mail("user@gmail.com")
                .password("123456")
                .build();

        Gson gson = new Gson();
        String json = gson.toJson(registerRequest);

        mockMvc.perform(post("/register")
                .contentType("application/json")
                .content(json))
                .andExpect(jsonPath("$.message", is("Email já cadastrado!")))
                .andExpect(status().isUnprocessableEntity());

    }
}
