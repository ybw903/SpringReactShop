package com.springreactshop.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springreactshop.demo.domain.*;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;


public class OrderDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        @NotEmpty
        private String username;

        @JsonProperty("address")
        private Address address;

        @NotEmpty
        private Payment payment;

        @JsonProperty("productList")
        private List<OrderProductDto> orderProducts;
    }

}
