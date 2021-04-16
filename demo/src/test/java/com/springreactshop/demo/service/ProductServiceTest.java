package com.springreactshop.demo.service;

import com.springreactshop.demo.domain.Product;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

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
        Page<Product> pageOfProduct =  productService.productsPages(pageable);

        //then
        assertThat(pageOfProduct.getTotalElements()).isEqualTo(expectedPageOfProduct.getTotalElements());
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