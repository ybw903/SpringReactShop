package com.springreactshop.demo.service;

import com.springreactshop.demo.domain.Product;
import com.springreactshop.demo.dto.ProductDto;
import com.springreactshop.demo.exception.ProductNotFoundException;
import com.springreactshop.demo.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    void 상품목록불러오기() {
        //given
        int page = 0;
        int pageSize =10;
        Pageable pageable = PageRequest.of(page,pageSize);
        Page<Product> expectedPageOfProduct = getPageOfProduct(pageSize);
        given(productRepository.findAll(pageable)).willReturn(expectedPageOfProduct);

        //when
        Page<ProductDto.Response> pageOfProduct =  productService.productsPages(pageable);

        //then
        assertThat(pageOfProduct.getTotalElements()).isEqualTo(expectedPageOfProduct.getTotalElements());
    }

    @Test
    void 상품추가() {
        //given
        ProductDto.Request productRequest = ProductDto.Request.builder().build();
        Product product = Product.builder().Id(0L).build();
        Product mockProduct = getMockProduct();

        given(productRepository.save(any(Product.class))).willReturn(mockProduct);

        //when
        ProductDto.Response productResponse = productService.addProduct(productRequest);

        //then
        assertThat(productResponse.getId()).isEqualTo(product.getId());
    }

    @Test
    void 등록되지않는단일상품을아이디로조회할때예외발생() {
        //given
        given(productRepository.findById(0L)).willThrow(ProductNotFoundException.class);

        //when&then
        assertThrows(ProductNotFoundException.class, ()->productService.getProductById(0L));
    }
    
    @Test
    void 단일상품을아이디로조회() {
        //given
        Product savedProduct = Product.builder().Id(0L).build();
        given(productRepository.findById(0L)).willReturn(Optional.of(savedProduct));

        //when
        Product foundProductById = productService.getProductById(0L);

        //then
        assertThat(foundProductById.getId()).isEqualTo(savedProduct.getId());
    }

    @Test
    void 아이디로조회한단일상품응답반환() {
        //given
        Product product = Product.builder().Id(0L).build();
        given(productRepository.findById(0L)).willReturn(Optional.of(product));

        //when
        ProductDto.Response productResponse  = productService.getProductResponseById(0L);

        //then
        assertThat(productResponse.getId()).isEqualTo(product.getId());
    }




    private Product getMockProduct() {
        return mock(Product.class);
    }

    private Page<Product> getPageOfProduct(int pageSize) {
        List<Product> products = new ArrayList<>();
        for(int i = 0; i < pageSize; i++) {
            products.add(getMockProduct());
        }
        return new PageImpl<>(products);
    }

}