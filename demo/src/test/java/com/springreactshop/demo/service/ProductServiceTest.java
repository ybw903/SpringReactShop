package com.springreactshop.demo.service;

import com.springreactshop.demo.domain.Product;
import com.springreactshop.demo.repository.ProductRepository;
import com.springreactshop.demo.representation.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

//    @Test
//    public void 상품추가() {
//
//        //Given
//        Product product = Product.builder()
//                .productName("테스트상품")
//                .productDescription("테스트상품 설명")
//                .productPrice(1000)
//                .build();
//
//        //When
//        productService.addProduct(product);
//
//        //Then
//        Product addedProduct = productRepository.findAll().get(0);
//        assertThat(addedProduct.getId()).isEqualTo(product.getId());
//        assertThat(addedProduct.getProductName()).isEqualTo(product.getProductName());
//        assertThat(addedProduct.getProductDescription()).isEqualTo(product.getProductDescription());
//        assertThat(addedProduct.getProductPrice()).isEqualTo(product.getProductPrice());
//    }
//
//    @Test
//    public void 상품추가ByDto() {
//
//        //Given
//        ProductDto productDto = new ProductDto();
//        productDto.setProductName("test");
//        productDto.setProductDescription("test-desc");
//        productDto.setProductPrice(100);
//
//        //When
//        Product product = productDto.toEntity();
//        productService.addProduct(product);
//
//        //Then
//        Product addedProduct = productRepository.findAll().get(0);
//        assertThat(addedProduct.getId()).isGreaterThan(product.getId());
//        assertThat(addedProduct.getProductName()).isEqualTo(product.getProductName());
//        assertThat(addedProduct.getProductDescription()).isEqualTo(product.getProductDescription());
//        assertThat(addedProduct.getProductPrice()).isEqualTo(product.getProductPrice());
//    }

}