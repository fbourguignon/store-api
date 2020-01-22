package com.store.unit.service;


import com.store.dto.ProductDTO;
import com.store.exception.StoreGenericException;
import com.store.model.Product;
import com.store.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    private static final UUID PRODUCT_ID = UUID.fromString("43d843d3-9561-42c0-ae04-f0d5ab83dee4");
    private static final ProductDTO PRODUCT_DTO = new ProductDTO().builder().name("new name").description("new description").build();
    private static final Product PRODUCT = new Product().builder().id(PRODUCT_ID).name("new name").description("new description").build();

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;

    @Test
    public void listMustCallProductRepositoryFindAll() {

        PageRequest pageRequest = PageRequest.of(0, 5);

        productService.list(pageRequest);

        verify(productRepository, times(1)).findAll(pageRequest);
    }

    @Test
    public void updateMustCallProductRepositorySave() {

        doReturn(Optional.of(PRODUCT)).when(productRepository).findById(PRODUCT_ID);

        productService.update(PRODUCT_DTO, PRODUCT_ID);

        verify(productRepository, times(1)).findById(PRODUCT_ID);
        verify(productRepository, times(1)).save(productArgumentCaptor.capture());

        Product capturedProduct = productArgumentCaptor.getValue();

        assertEquals(PRODUCT_ID, capturedProduct.getId());
        assertEquals(PRODUCT.getDescription(), capturedProduct.getDescription());
        assertEquals(PRODUCT.getName(), capturedProduct.getName());
    }

    @Test
    public void saveMustCallProductRepositorySave() {

        doReturn(Optional.of(PRODUCT)).when(productRepository).findById(PRODUCT_ID);

        Product productReturn = productService.get(PRODUCT_ID);

        verify(productRepository, times(1)).findById(PRODUCT_ID);

        assertEquals(productReturn.getId(), PRODUCT_ID);
        assertEquals(productReturn.getDescription(), PRODUCT_DTO.getDescription());
        assertEquals(productReturn.getName(), PRODUCT_DTO.getName());
    }

    @Test
    public void getMustCallProductRepositoryFindById() {

        productService.save(PRODUCT_DTO);

        verify(productRepository, times(1)).save(productArgumentCaptor.capture());

        Product capturedProduct = productArgumentCaptor.getValue();

        assertEquals(PRODUCT_DTO.getDescription(), capturedProduct.getDescription());
        assertEquals(PRODUCT_DTO.getName(), capturedProduct.getName());
    }

    @Test(expected = StoreGenericException.class)
    public void getMustThrowExeceptionWhenProductNotExists() {
        doReturn(null).when(productRepository).findById(any());
        productService.get(PRODUCT_ID);
    }

    @Test(expected = StoreGenericException.class)
    public void updateMustThrowsExceptionWhenProductNotExists() {
        doReturn(null).when(productRepository).findById(any());
        productService.update(PRODUCT_DTO, PRODUCT_ID);
    }

    @Test
    public void deleteMustCallProductRepositoryDelete() {

        final var product = new Product()
                .builder()
                .id(PRODUCT_ID)
                .build();

        doReturn(Optional.of(product)).when(productRepository).findById(PRODUCT_ID);

        productService.delete(PRODUCT_ID);

        verify(productRepository, times(1)).findById(PRODUCT_ID);
        verify(productRepository, times(1)).delete(productArgumentCaptor.capture());

        Product capturedProduct = productArgumentCaptor.getValue();

        assertEquals(PRODUCT_ID, capturedProduct.getId());
    }

    @Test(expected = StoreGenericException.class)
    public void deleteMustThrowsExceptionWhenProductNotExists() {
        doReturn(null).when(productRepository).findById(any());
        productService.delete(PRODUCT_ID);
    }

}
