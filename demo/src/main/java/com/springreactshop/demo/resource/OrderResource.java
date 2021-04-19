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

    private final MemberResource memberResource;

    private final Delivery delivery;

    private final List<OrderProductDto> orderProducts ;

    private final OrderStatus status;

    private final int totalPrice;

    private final Date orderDate;

    public OrderResource(OrderDto.Response orderResponse) {
        this.id = orderResponse.getId();
        this.memberResource =  new MemberResource(orderResponse.getMemberResponse());
        this.delivery = orderResponse.getDelivery();
        this.orderProducts = orderResponse.getOrderProducts();
        this.status = orderResponse.getStatus();
        this.totalPrice = orderResponse.getTotalPrice();
        this.orderDate = orderResponse.getOrderDate();
        add(linkTo(OrderController.class).slash(this.id).withSelfRel());
        addIf(status==OrderStatus.ORDER,
                ()->linkTo(OrderController.class).slash(this.id).withRel("cancel-order"));
    }
}
