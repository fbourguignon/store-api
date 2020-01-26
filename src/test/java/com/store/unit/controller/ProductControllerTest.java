package com.store.unit.controller;


import com.store.StoreApiApplication;
import com.store.config.SpringSecurityConfigTest;
import com.store.model.Product;
import com.store.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = { SpringSecurityConfigTest.class, StoreApiApplication.class }
)
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    @Test
    @WithUserDetails("admin_user@store.com.br")
    public void givenProducts_whenGetProduts_thenReturnJsonPageable()
            throws Exception {


        given(productService.list(any())).willReturn(mockProdutsPage());

        mvc.perform(get("/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.totalElements", is(1)))
                .andExpect(jsonPath("$.content[0].name", is("Zenfone")));
    }

    @Test
    @WithUserDetails("simple_user@store.com.br")
    public void givenProducts_whenGetProduts_MustThrowExceptionInSimpleUser()
            throws Exception {


        given(productService.list(any())).willReturn(mockProdutsPage());

        mvc.perform(get("/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    private Page<Product> mockProdutsPage(){
        Product product = Product.builder().name("Zenfone").build();
        Page<Product> pagedResponse = new PageImpl(Arrays.asList(product));
        return pagedResponse;
    }

}
