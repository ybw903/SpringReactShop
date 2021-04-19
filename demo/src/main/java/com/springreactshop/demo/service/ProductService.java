package com.springreactshop.demo.service;

import com.springreactshop.demo.domain.Product;
import com.springreactshop.demo.dto.ProductDto;
import com.springreactshop.demo.exception.ProductNotFoundException;
import com.springreactshop.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDto addProduct(ProductDto productRequest) {
        Product product = productRequest.toEntityWithOutId();
        Product saved = productRepository.save(product);
        return ProductDto.from(saved);
    }

    public Page<ProductDto> productsPages(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        List<ProductDto> productsResponse = productPage
                                                    .getContent().stream()
                                                    .map(ProductDto::from)
                                                    .collect(Collectors.toList());
        return new PageImpl<>(productsResponse,pageable,productPage.getTotalElements());
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    public ProductDto getProductResponseById(Long id) {
        Product product = getProductById(id);
        return ProductDto.from(product);
    }

    public ProductDto update(Long id, ProductDto productRequest) {
        Product existingProduct = getProductById(id);
        Product updated = existingProduct.update(productRequest);
        return ProductDto.from(updated);
    }

    public void deleteProduct(Long id) {
        getProductById(id);
        productRepository.deleteById(id);
    }

}
