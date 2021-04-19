package com.springreactshop.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springreactshop.demo.domain.Address;
import com.springreactshop.demo.domain.OrderProduct;
import com.springreactshop.demo.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProductDto {

    @JsonProperty("product")
    private ProductDto productDto;

    private int orderPrice;

    private int orderQuantity;

    public static OrderProductDto of(OrderProduct orderProduct) {
        return OrderProductDto.builder()
                .productDto(ProductDto.from(orderProduct.getProduct()))
                .orderPrice(orderProduct.getOrderPrice())
                .orderQuantity(orderProduct.getCount())
                .build();
    }
}
