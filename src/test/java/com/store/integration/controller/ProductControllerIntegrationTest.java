package com.store.integration.controller;

import com.store.dto.ProductDTO;
import com.store.integration.response.RestResponsePage;
import com.store.model.Product;
import com.store.repository.ProductRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class ProductControllerIntegrationTest extends AbstractControllerIntegrationTests {

    @Test
    public void mustLisWithPaginationProducts() {

        ParameterizedTypeReference<RestResponsePage<Product>> responseType = new ParameterizedTypeReference<RestResponsePage<Product>>() {
        };
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", getJWTAdmin());
        headers.add("Content-Type", "application/json");

        HttpEntity<PageRequest> pageRequest = new HttpEntity<>(headers);
        ResponseEntity<RestResponsePage<Product>> response = restTemplate.exchange("/products", HttpMethod.GET, pageRequest/*httpEntity*/, responseType);
        RestResponsePage<Product> responseDTO = response.getBody();

        assertEquals(responseDTO.getTotalPages(), 3);
        assertEquals(responseDTO.getTotalElements(), 15);
    }

    @Test
    public void mustCreateProduct() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", getJWTAdmin());
        headers.add("Content-Type", "application/json");

        ProductDTO productDTO = ProductDTO.builder().name("ProdutoCreate").description("Description").build();
        HttpEntity<ProductDTO> request = new HttpEntity<>(productDTO, headers);

        ResponseEntity<Product> response = restTemplate.exchange("/products", HttpMethod.POST, request/*httpEntity*/, Product.class);
        Product product = response.getBody();

        assertNotNull(product.getId());
        assertEquals(product.getName(), productDTO.getName());
    }

}
