package com.store.integration.controller;

import com.store.dto.ProductDTO;
import com.store.dto.ResponseDTO;
import com.store.integration.response.RestResponsePage;
import com.store.model.Product;
import com.store.repository.ProductRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class ProductControllerIntegrationTest extends AbstractControllerIntegrationTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void mustLisWithPaginationProducts() {

        ParameterizedTypeReference<RestResponsePage<Product>> responseType = new ParameterizedTypeReference<RestResponsePage<Product>>() {
        };
        HttpHeaders headers = getAuthenticatedHeaders();

        HttpEntity<PageRequest> pageRequest = new HttpEntity<>(headers);
        ResponseEntity<RestResponsePage<Product>> response = restTemplate.exchange("/products", HttpMethod.GET, pageRequest/*httpEntity*/, responseType);
        RestResponsePage<Product> responseDTO = response.getBody();

        assertEquals(responseDTO.getTotalPages(), 3);
        assertEquals(responseDTO.getTotalElements(), 15);
    }

    @Test
    public void mustCreateProduct() {

        HttpHeaders headers = getAuthenticatedHeaders();

        ProductDTO productDTO = ProductDTO.builder().name("ProdutoCreate").description("Description").build();
        HttpEntity<ProductDTO> request = new HttpEntity<>(productDTO, headers);

        restTemplate.exchange("/products", HttpMethod.POST, request/*httpEntity*/, Product.class);

        Product product = productRepository.findByName("ProdutoCreate");

        assertNotNull(product);
        assertEquals(product.getName(), productDTO.getName());
        assertEquals(product.getDescription(), productDTO.getDescription());
    }

    @Test
    public void mustDeleteProduct() {

        HttpHeaders headers = getAuthenticatedHeaders();

        HttpEntity<ProductDTO> request = new HttpEntity<>(headers);

        restTemplate.exchange("/products/aa3355b8-da98-478f-8a9a-aef10739e416", HttpMethod.DELETE, request/*httpEntity*/, ResponseDTO.class);

        assertTrue(productRepository.findById(UUID.fromString("aa3355b8-da98-478f-8a9a-aef10739e416")).isEmpty());
    }

    @Test
    public void mustEditProduct() {

        HttpHeaders headers = getAuthenticatedHeaders();
        ProductDTO productDTO = ProductDTO.builder().name("ProductEdited").description("Description").build();

        HttpEntity<ProductDTO> request = new HttpEntity<>(productDTO,headers);

        restTemplate.exchange("/products/aa3355b8-da98-478f-8a9a-aef10739e416", HttpMethod.PUT, request/*httpEntity*/, ResponseDTO.class);

        Product product = productRepository.findById(UUID.fromString("aa3355b8-da98-478f-8a9a-aef10739e416")).get();

        assertNotNull(product);
        assertEquals(product.getName(), productDTO.getName());
        assertEquals(product.getDescription(), productDTO.getDescription());
    }

    private HttpHeaders getAuthenticatedHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", getJWTAdmin());
        headers.add("Content-Type", "application/json");
        return headers;
    }

}
