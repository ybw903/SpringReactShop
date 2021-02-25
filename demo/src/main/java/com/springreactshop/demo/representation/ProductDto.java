package com.springreactshop.demo.representation;

import com.springreactshop.demo.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProductDto {

    private String productName;

    private String productDescription;

    private int productPrice;

    public Product toEntity(){
        return Product.builder()
                .productName(productName)
                .productDescription(productDescription)
                .productPrice(productPrice)
                .build();
    }
}
