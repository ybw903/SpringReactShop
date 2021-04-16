package com.springreactshop.demo.dto;

import com.springreactshop.demo.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class ProductDto {

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
