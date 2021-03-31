package com.springreactshop.demo.representation;

import com.springreactshop.demo.domain.Order;
import com.springreactshop.demo.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private String productName;

    private String productDescription;

    private int productPrice;

}

