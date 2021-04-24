package com.springreactshop.demo.dto;

import com.springreactshop.demo.domain.Category;
import com.springreactshop.demo.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


public class ProductDto {

    @Data @NoArgsConstructor @AllArgsConstructor
    @Builder
    public static class Request {
        private String productName;

        private String productDescription;

        private int productPrice;

        private int productQuantity;

        private Category category;

        public Product toEntity(){
            return Product.builder()
                    .productName(productName)
                    .productDescription(productDescription)
                    .productPrice(productPrice)
                    .productQuantity(productQuantity)
                    .category(category)
                    .build();
        }
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    @Builder
    public static class Info{
        private Long id;

        private String productName;

        private String productDescription;

        private int productPrice;

        private int productQuantity;

        private Category category;

        public  Product toEntity(){
            return Product.builder()
                    .id(id)
                    .productName(productName)
                    .productDescription(productDescription)
                    .productPrice(productPrice)
                    .productQuantity(productQuantity)
                    .category(category)
                    .build();
        }

        public static Info from(Product product) {
            return Info.builder()
                    .id(product.getId())
                    .productName(product.getProductName())
                    .productDescription(product.getProductName())
                    .productPrice(product.getProductPrice())
                    .productQuantity(product.getProductQuantity())
                    .category(product.getCategory())
                    .build();
        }
    }
}
