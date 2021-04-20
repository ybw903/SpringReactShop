package com.springreactshop.demo.configuration;

import com.springreactshop.demo.dto.ProductDto;
import com.springreactshop.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {

    @Bean
    public ApplicationRunner applicationRunner(){
        return new ApplicationRunner() {

            @Autowired
            ProductService productService;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                for(int i = 0; i<=1000; i++) {
                    ProductDto.Request productRequest = ProductDto.Request.builder()
                            .productName("test" + i)
                            .productDescription("testDesc" + i)
                            .productPrice(i*100)
                            .productQuantity(9999)
                            .build();
                    productService.addProduct(productRequest);
                }


            }
        };
    }
}
