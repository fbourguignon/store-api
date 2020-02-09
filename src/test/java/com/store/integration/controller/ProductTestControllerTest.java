package com.store.integration.controller;

import com.store.integration.response.RestResponsePage;
import com.store.model.Product;
import com.store.repository.ProductRepository;
import com.store.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;


import static org.junit.Assert.assertEquals;



public class ProductTestControllerTest extends BaseIntegrationTestController {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Before
    public void before(){
        persistAdminUser();
        persistTestProducts();
    }


    @Test
    public void mustListProducts(){

        ParameterizedTypeReference<RestResponsePage<Product>> responseType = new ParameterizedTypeReference<RestResponsePage<Product>>() { };

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", getJWTAdmin());

        HttpEntity<PageRequest> pageRequest = new HttpEntity<>(headers);

        ResponseEntity<RestResponsePage<Product>> result = restTemplate.exchange("/products/list", HttpMethod.GET, pageRequest, responseType);
        RestResponsePage<Product> response = result.getBody();

        assertEquals(response.getTotalElements(),2);
    }

    public void persistTestProducts() {
        productRepository.save(Product.builder().name("Motorola 1").description("Whaterproof phone 1").build());
        productRepository.save(Product.builder().name("Motorola 2").description("Whaterproof phone 2").build());
        productRepository.save(Product.builder().name("Motorola 3").description("Whaterproof phone 3").build());
        productRepository.save(Product.builder().name("Motorola 4").description("Whaterproof phone 4").build());
        productRepository.save(Product.builder().name("Motorola 5").description("Whaterproof phone 5").build());
        productRepository.findAll();
    }




}
