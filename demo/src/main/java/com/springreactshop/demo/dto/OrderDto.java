package com.springreactshop.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springreactshop.demo.domain.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;

        private MemberDto.InfoResponse
                memberResponse;

        private Delivery delivery;

        private List<OrderProductDto> orderProducts;

        private OrderStatus status;

        private Payment payment;

        private int totalPrice;

        private Date orderDate;

        public static Response of(Order order) {
            return Response.builder()
                    .id(order.getId())
                    .memberResponse(MemberDto.InfoResponse.of(order.getMember()))
                    .delivery(order.getDelivery())
                    .orderProducts(
                            order.getOrderProducts()
                                    .stream().map(OrderProductDto::of).collect(Collectors.toList()))
                    .payment(order.getPayment())
                    .status(order.getStatus())
                    .totalPrice(order.getTotalPrice())
                    .orderDate(order.getOrderDate())
                    .build();
        }
    }


}
