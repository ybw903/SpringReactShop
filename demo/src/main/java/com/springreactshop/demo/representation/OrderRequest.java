package com.springreactshop.demo.representation;

import com.springreactshop.demo.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private String username;

    private Address address;

    private List<ProductRequest> productList;

}

