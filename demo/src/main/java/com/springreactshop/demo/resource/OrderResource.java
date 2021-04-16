package com.springreactshop.demo.resource;

import com.springreactshop.demo.controller.OrderController;
import com.springreactshop.demo.domain.*;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Getter
public class OrderResource extends RepresentationModel<OrderResource> {

    private final Long id;

    private final Member member;

    private final Delivery delivery;

    private final List<OrderProduct> orderProducts ;

    private final OrderStatus status;

    private final int totalPrice;

    private final Date orderDate;

    public OrderResource(Order order) {
        this.id = order.getId();
        this.member = order.getMember();
        this.delivery = order.getDelivery();
        this.orderProducts = order.getOrderProducts();
        this.totalPrice = order.getTotalPrice();
        this.orderDate = order.getOrderDate();
        this.status = order.getStatus();
        add(linkTo(OrderController.class).slash(order.getId()).withSelfRel());
    }
}
