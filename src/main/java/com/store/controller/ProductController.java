package com.store.controller;

import com.store.dto.ProductDTO;
import com.store.dto.ResponseDTO;
import com.store.model.Product;
import com.store.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
@Api(value = "Product", description = "Actions para as operações com produto")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Secured("ROLE_ADMIN")
    @GetMapping
    @ApiOperation(value = "Lista os produtos")
    public Page<Product> list(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                              @RequestParam(value = "size", defaultValue = "5", required = false) Integer size){
        return productService.list(PageRequest.of(page, size));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    @ApiOperation(value = "Salva um produto")
    public ResponseEntity<Product> save(@RequestBody ProductDTO productDTO){
        return ResponseEntity.accepted().body(productService.save(productDTO));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    @ApiOperation(value = "Recupera um produto pelo id")
    public ResponseEntity<Product> get(@PathVariable UUID id){
        return ResponseEntity.ok(productService.get(id));
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    @ApiOperation(value = "Atualiza um produto")
    public ResponseEntity<ResponseDTO> update(@RequestBody ProductDTO productDTO,@PathVariable UUID id){
       productService.update(productDTO,id);
        return ResponseEntity.ok(new ResponseDTO("Produto alterado com sucesso!"));
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Remove um produto")
    public ResponseEntity<ResponseDTO> delete(@PathVariable UUID id){
        productService.delete(id);
        return ResponseEntity.ok(new ResponseDTO("Produto removido com sucesso!"));
    }


}
