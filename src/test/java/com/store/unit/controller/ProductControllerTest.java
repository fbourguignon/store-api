package com.store.unit.controller;


import com.google.gson.Gson;
import com.store.StoreApiApplication;
import com.store.config.SpringSecurityConfigTest;
import com.store.dto.ProductDTO;
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
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    private String uuid = UUID.randomUUID().toString();

    @Test
    @WithUserDetails("admin_user@store.com.br")
    public void mustReturnPageableListOnCallProductListWhenUserIsAdmin()
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
    @WithUserDetails("admin_user@store.com.br")
    public void mustReturnProductOnCallGetProductWhenUserIsAdmin()
            throws Exception {


        given(productService.get(UUID.fromString(uuid))).willReturn(mockProduct());

        mvc.perform(get("/products/"+uuid)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(uuid)))
                .andExpect(jsonPath("$.name", is("Motorola X1")));
    }

    @Test
    @WithUserDetails("admin_user@store.com.br")
    public void mustReturnProductOnCallUpdateProductWhenUserIsAdmin()
            throws Exception {

        Gson gson = new Gson();
        String json = gson.toJson(mockProductDTO());

        mvc.perform(put("/products/"+uuid)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Produto alterado com sucesso!")));
    }

    @Test
    @WithUserDetails("admin_user@store.com.br")
    public void mustReturnProductOnCallSaveProductWhenUserIsAdmin()
            throws Exception {

        ProductDTO productDTO = mockProductDTO();
        given(productService.save(productDTO)).willReturn(mockProduct());

        Gson gson = new Gson();
        String json = gson.toJson(productDTO);

        mvc.perform(post("/products")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Motorola X1")));
    }

    @Test
    @WithUserDetails("admin_user@store.com.br")
    public void mustReturnProductOnCallDeleteProductWhenUserIsAdmin()
            throws Exception {

        mvc.perform(delete("/products/"+uuid)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Produto removido com sucesso!")));
    }

    @Test
    @WithUserDetails("simple_user@store.com.br")
    public void mustThrowExceptionWhenSimpleUserTryListProducts()
            throws Exception {


        given(productService.list(any())).willReturn(mockProdutsPage());

        mvc.perform(get("/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("simple_user@store.com.br")
    public void mustThrowExceptionWhenSimpleUserTryDeleteProduct()
            throws Exception {

        mvc.perform(delete("/products/"+uuid)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("simple_user@store.com.br")
    public void mustThrowExceptionWhenSimpleUserTrySaveProduct()
            throws Exception {

        Gson gson = new Gson();
        String json = gson.toJson(mockProductDTO());

        mvc.perform(post("/products")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("simple_user@store.com.br")
    public void mustThrowExceptionWhenSimpleUserTryEditProduct()
            throws Exception {

        Gson gson = new Gson();
        String json = gson.toJson(mockProductDTO());

        mvc.perform(put("/products/"+uuid)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("simple_user@store.com.br")
    public void mustThrowExceptionWhenSimpleUserTryGetProduct()
            throws Exception {

        mvc.perform(get("/products/"+uuid)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }


    private Page<Product> mockProdutsPage(){
        Product product = Product.builder().name("Zenfone").build();
        Page<Product> pagedResponse = new PageImpl(Arrays.asList(product));
        return pagedResponse;
    }

    private ProductDTO mockProductDTO(){
        return ProductDTO.builder().name("Motorola X1").description("Powerful Smarthphone").build();
    }

    private Product mockProduct(){
        return Product.builder().id(UUID.fromString(uuid)).name("Motorola X1").description("Powerful Smarthphone").build();
    }
}
