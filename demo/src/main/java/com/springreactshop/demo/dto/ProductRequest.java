package com.springreactshop.demo.dto;

import com.springreactshop.demo.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    private Long id;

    private String productName;

    private String productDescription;

    private int productPrice;

    private int productQuantity;

    private int orderPrice;

    private int orderQuantity;

    public Product toEntity(){
        return Product.builder()
                .Id(id)
                .productName(productName)
                .productDescription(productDescription)
                .productPrice(productPrice)
                .productQuantity(productQuantity)
                .build();
    }
}
