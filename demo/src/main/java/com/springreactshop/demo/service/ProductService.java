package com.springreactshop.demo.service;

import com.springreactshop.demo.domain.Product;
import com.springreactshop.demo.dto.ProductDto;
import com.springreactshop.demo.exception.ProductNotFoundException;
import com.springreactshop.demo.repository.ProductRepository;
import com.springreactshop.demo.resource.ProductResource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public Product addProduct(ProductDto.Request productRequest) {
        Product product = productRequest.toEntity();
        Product saved = productRepository.save(product);
        return saved;
    }

    public Page<Product> productsPages(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);

        return productPage ;
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    public Product getProductResponseById(Long id) {
        Product product = getProductById(id);
        return product;
    }

    public Product update(Long id, ProductDto.Request productRequest) {
        Product existingProduct = getProductById(id);
        Product updated = existingProduct.update(productRequest);
        return updated;
    }

    public void deleteProduct(Long id) {
        getProductById(id);
        productRepository.deleteById(id);
    }

}
