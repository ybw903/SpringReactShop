package com.springreactshop.demo.service;

import com.springreactshop.demo.domain.Product;
import com.springreactshop.demo.dto.ProductDto;
import com.springreactshop.demo.exception.ProductNotFoundException;
import com.springreactshop.demo.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ProductService {

    private ProductRepository productRepository;

    public ProductDto.Response addProduct(ProductDto.Request productRequest) {
        Product product = productRequest.toEntity();
        Product saved = productRepository.save(product);
        return ProductDto.Response.of(saved);
    }

    public Page<ProductDto.Response> productsPages(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        List<ProductDto.Response> productsResponse = productPage
                                                    .getContent().stream()
                                                    .map(ProductDto.Response::of)
                                                    .collect(Collectors.toList());
        return new PageImpl<>(productsResponse,pageable,productPage.getTotalElements());
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    public ProductDto.Response getProductResponseById(Long id) {
        Product product = getProductById(id);
        return ProductDto.Response.of(product);
    }

    public ProductDto.Response update(Long id, ProductDto.Request productRequest) {
        Product existingProduct = getProductById(id);
        Product updated = existingProduct.update(productRequest);
        return ProductDto.Response.of(updated);
    }

    public void deleteProduct(Long id) {
        getProductById(id);
        productRepository.deleteById(id);
    }

}
