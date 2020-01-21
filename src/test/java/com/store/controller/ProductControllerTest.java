package com.store.controller;


import com.store.dto.ProductDTO;
import com.store.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Captor
    private ArgumentCaptor<PageRequest> pageRequestArgumentCaptor;

    @Test
    public void listMustCallProductServiceList() {

        productController.list(0, 5);

        verify( productService, times(1) ).list( pageRequestArgumentCaptor.capture() );

        PageRequest pageRequestCapture =  pageRequestArgumentCaptor.getValue();

        assertEquals(5, pageRequestCapture.getPageSize());
        assertEquals(0, pageRequestCapture.getPageNumber());

    }

    @Test
    public void saveMustCallProductServiceSave() {
        final ProductDTO productDTO = new ProductDTO().builder()
                                            .name("Smarthpone")
                                            .description("A very powerful gadget")
                                            .build();

        productController.save(productDTO);

        verify( productService, only() ).save( productDTO );
    }

    @Test
    public void updateMustCallProductServiceUpdate() {
        final UUID uuid = UUID.fromString("43d843d3-9561-42c0-ae04-f0d5ab83dee4");
        final ProductDTO productDTO = new ProductDTO().builder()
                .name("Smarthpone")
                .description("A very powerful gadget")
                .build();

        productController.update(productDTO,uuid);

        verify( productService, only() ).update( productDTO,uuid );
    }

    @Test
    public void deleteMustCallProductServiceDelete() {
        final UUID uuid = UUID.fromString("43d843d3-9561-42c0-ae04-f0d5ab83dee4");

        productController.delete(uuid);

        verify( productService, only() ).delete( uuid );
    }

}
