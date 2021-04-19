package com.springreactshop.demo.dto;

import com.springreactshop.demo.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class ProductDto {

    private Long id;

    private String productName;

    private String productDescription;

    private int productPrice;

    private int productQuantity;

    public Product toEntityWithOutId(){
        return Product.builder()
                .productName(productName)
                .productDescription(productDescription)
                .productPrice(productPrice)
                .productQuantity(productQuantity)
                .build();
    }

    public Product toEntityWithId(){
        return Product.builder()
                .Id(id)
                .productName(productName)
                .productDescription(productDescription)
                .productPrice(productPrice)
                .productQuantity(productQuantity)
                .build();
    }

    public static ProductDto from(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productDescription(product.getProductDescription())
                .productPrice(product.getProductPrice())
                .productQuantity(product.getProductQuantity())
                .build();
    }

}
