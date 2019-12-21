package com.store.controller;

import com.store.dto.ProductDTO;
import com.store.dto.ResponseDTO;
import com.store.model.Product;
import com.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Secured("ROLE_ADMINISTRADOR")
    @GetMapping
    public Page<Product> list(Pageable pageable){
        return productService.list(pageable);
    }

    @Secured("ROLE_ADMINISTRADOR")
    @PostMapping
    public ResponseEntity<Product> save(@RequestBody ProductDTO productDTO){
        return ResponseEntity.accepted().body(productService.save(productDTO));
    }

    @Secured("ROLE_ADMINISTRADOR")
    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable UUID id){
        return ResponseEntity.ok(productService.get(id));
    }

    @Secured("ROLE_ADMINISTRADOR")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> update(@PathVariable Product product){
       productService.update(product);
        return ResponseEntity.ok(new ResponseDTO("Produto alterado com sucesso!"));
    }

    @Secured("ROLE_ADMINISTRADOR")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable UUID id){
        productService.remove(id);
        return ResponseEntity.ok(new ResponseDTO("Produto removido com sucesso!"));
    }


}
