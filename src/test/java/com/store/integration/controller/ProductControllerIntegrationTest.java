package com.store.integration.controller;

import com.store.integration.response.RestResponsePage;
import com.store.model.Product;
import com.store.repository.ProductRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ProductControllerIntegrationTest extends AbstractControllerIntegrationTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void mustLoginWithValidCredentials() {

        persistProducts();

        ParameterizedTypeReference<RestResponsePage<Product>> responseType = new ParameterizedTypeReference<RestResponsePage<Product>>() {
        };
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", getJWTAdmin());
        headers.add("Content-Type", "application/json");

        HttpEntity<PageRequest> pageRequest = new HttpEntity<>(headers);
        ResponseEntity<RestResponsePage<Product>> response = restTemplate.exchange("/products", HttpMethod.GET, pageRequest/*httpEntity*/, responseType);
        RestResponsePage<Product> responseDTO = response.getBody();

        assertEquals(responseDTO.getTotalPages(),3);
        assertEquals(responseDTO.getTotalElements(),15);
    }


    private void persistProducts() {
        List<Product> products = new ArrayList<>();

        products.add(Product.builder().name("Product 1").description("Product 1 description").build());
        products.add(Product.builder().name("Product 2").description("Product 2 description").build());
        products.add(Product.builder().name("Product 3").description("Product 3 description").build());
        products.add(Product.builder().name("Product 4").description("Product 4 description").build());
        products.add(Product.builder().name("Product 5").description("Product 5 description").build());
        products.add(Product.builder().name("Product 6").description("Product 6 description").build());
        products.add(Product.builder().name("Product 7").description("Product 7 description").build());
        products.add(Product.builder().name("Product 8").description("Product 8 description").build());
        products.add(Product.builder().name("Product 9").description("Product 9 description").build());
        products.add(Product.builder().name("Product 10").description("Product 10 description").build());
        products.add(Product.builder().name("Product 11").description("Product 11 description").build());
        products.add(Product.builder().name("Product 12").description("Product 12 description").build());
        products.add(Product.builder().name("Product 13").description("Product 13 description").build());
        products.add(Product.builder().name("Product 14").description("Product 14 description").build());
        products.add(Product.builder().name("Product 15").description("Product 15 description").build());

        for (Product p:products) {
            productRepository.save(p);
        }

    }
}
