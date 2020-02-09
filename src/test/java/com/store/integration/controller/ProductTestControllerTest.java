package com.store.integration.controller;

import com.store.model.Product;
import com.store.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class ProductTestControllerTest extends BaseIntegrationTestController {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Before
    public void before(){
        persistAdminUser();
    }

    @Test
    public void mustCreateProduct(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", getJWTAdmin());
        headers.add("Content-Type", "application/json");

        HttpEntity<Product> pageRequest = new HttpEntity<>(Product.builder().name("Xbox one").description("Console").build(),headers);
        final ResponseEntity<Product> response = restTemplate.exchange("/products", HttpMethod.POST, pageRequest, Product.class);

        Product product = response.getBody();
        assertEquals(product.getName(),"Xbox one");
        assertEquals(product.getDescription(),"Console");
        assertNotNull(product.getId());
    }

}
