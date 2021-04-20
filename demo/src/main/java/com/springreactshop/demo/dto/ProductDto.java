package com.springreactshop.demo.dto;

import com.springreactshop.demo.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class ProductDto {

    @Data @NoArgsConstructor @AllArgsConstructor
    @Builder
    public static class Request {
        private String productName;

        private String productDescription;

        private int productPrice;

        private int productQuantity;

        public Product toEntity(){
            return Product.builder()
                    .productName(productName)
                    .productDescription(productDescription)
                    .productPrice(productPrice)
                    .productQuantity(productQuantity)
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

        public  Product toEntity(){
            return Product.builder()
                    .Id(id)
                    .productName(productName)
                    .productDescription(productDescription)
                    .productPrice(productPrice)
                    .productQuantity(productQuantity)
                    .build();
        }

        public static Info from(Product product) {
            return Info.builder()
                    .id(product.getId())
                    .productName(product.getProductName())
                    .productDescription(product.getProductName())
                    .productPrice(product.getProductPrice())
                    .productQuantity(product.getProductQuantity())
                    .build();
        }
    }
}
