package com.springreactshop.demo.repository;

import com.springreactshop.demo.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductRepositorySupportTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductRepositorySupport productRepositorySupport;

    @Test
    public void querydsl_기본_테스트() {
        List<Product> productsNamedTest1 = productRepositorySupport.findByName("test1");

        //then
        assertThat(productsNamedTest1.size()).isGreaterThanOrEqualTo(1);
    }
}