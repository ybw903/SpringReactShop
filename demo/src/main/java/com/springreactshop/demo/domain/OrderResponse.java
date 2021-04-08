package com.springreactshop.demo.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderResponse {

    private Long id;

    private Member member;

    private Delivery delivery;

    private List<OrderProduct> orderProducts = new ArrayList<>();

    private OrderStatus status;

    private int totalPrice;

    private Date orderDate;

}
