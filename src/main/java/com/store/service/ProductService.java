package com.store.service;

import com.store.dto.ProductDTO;
import com.store.exception.StoreBusinessException;
import com.store.exception.StoreGenericException;
import com.store.exception.StoreNotFoundException;
import com.store.model.Product;
import com.store.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> list(Pageable pageable){
        try{
             return productRepository.findAll(pageable);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new StoreGenericException("Houve um erro ao listar os produtos");
        }
    }

    public Product get(UUID uuid){
        try {
            return productRepository
                    .findById(uuid)
                    .orElseThrow(() -> new StoreNotFoundException("Produto não localizado"));
        } catch (StoreNotFoundException s) {
            log.warn("O produto com id [{}] não foi localizado",uuid);
            throw s;
        } catch (Exception e){
            log.error(e.getMessage());
            throw new StoreGenericException("Houve um erro ao recuperar o produto");
        }

    }

    public void remove(UUID uuid){
        try {
            productRepository.findById(uuid)
                    .ifPresentOrElse(
                            product -> productRepository.delete(product),
                            () -> new StoreNotFoundException("Produto não encontrado para remoção"));
        } catch (StoreNotFoundException s) {
            log.warn("O produto com id [{}] não foi localizado para remoção",uuid);
            throw s;
        } catch (Exception e){
            log.error(e.getMessage());
            throw new StoreGenericException("Houve um erro ao remover o produto");
        }

    }

    public Product save(ProductDTO productDTO){
        try {
            return productRepository.save(
                    Product.builder()
                            .name(productDTO.getName())
                            .description(productDTO.getDescription())
                            .build());
        }catch (DataIntegrityViolationException de) {
            log.error("O nome [{}] já existe em nossos registros",productDTO.getName());
            throw new StoreBusinessException("Nome de produto já cadastrado!");
        } catch (Exception e){
            log.error(e.getMessage());
            throw new StoreGenericException("Houve um erro ao salvar o produto");
        }

    }

    public void update(Product product){
        try {
            productRepository.findById(product.getId())
                    .ifPresentOrElse(
                            p -> productRepository.save(
                                    Product.builder()
                                            .description(p.getDescription())
                                            .name(p.getName())
                                            .build()),
                            () -> new StoreNotFoundException("Produto não encontrado para remoção"));
        } catch (StoreNotFoundException s) {
            log.warn("O produto com id [{}] não foi localizado para alteração",product.getId());
            throw s;
        }catch (DataIntegrityViolationException de) {
            log.error("O nome [{}] já existe em nossos registros",product.getName());
            throw new StoreBusinessException("Nome de produto já cadastrado!");
        } catch (Exception e){
            log.error(e.getMessage());
            throw new StoreGenericException("Houve um erro ao salvar o produto");
        }

    }


}
