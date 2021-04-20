package com.springreactshop.demo.resource;

import com.springreactshop.demo.controller.MemberController;
import com.springreactshop.demo.controller.OrderController;
import com.springreactshop.demo.domain.*;
import com.springreactshop.demo.dto.MemberDto;
import com.springreactshop.demo.dto.OrderDto;
import com.springreactshop.demo.dto.OrderProductDto;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Getter
public class OrderResource extends RepresentationModel<OrderResource> {

    private final Long id;

    private final MemberResource member;

    private final Delivery delivery;

    private final List<OrderProduct> orderProducts ;

    private final OrderStatus status;

    private final int totalPrice;

    private final Date orderDate;

    public OrderResource(Order order) {
        this.id = order.getId();
        this.member =  new MemberResource(order.getMember());
        this.delivery = order.getDelivery();
        this.orderProducts = order.getOrderProducts();
        this.status = order.getStatus();
        this.totalPrice = order.getTotalPrice();
        this.orderDate = order.getOrderDate();
        add(linkTo(OrderController.class).slash(this.id).withSelfRel());
        addIf(status==OrderStatus.ORDER,
                ()->linkTo(OrderController.class).slash(this.id).withRel("cancel-order"));
    }
}
