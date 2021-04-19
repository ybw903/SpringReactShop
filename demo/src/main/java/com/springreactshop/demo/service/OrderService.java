package com.springreactshop.demo.service;

import com.springreactshop.demo.domain.*;
import com.springreactshop.demo.dto.OrderDto;
import com.springreactshop.demo.dto.ProductDto;
import com.springreactshop.demo.exception.OrderNotFoundException;
import com.springreactshop.demo.repository.OrderRepository;
import com.springreactshop.demo.resource.OrderResource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class OrderService {

    private final MemberService memberService;

    private final OrderRepository orderRepository;

    public OrderDto.Response order(OrderDto.Request orderRequest) {
        Member member = memberService.findMemberByUsername(orderRequest.getUsername());
        Delivery delivery = Delivery.createDelivery(orderRequest.getAddress());

        List<OrderProduct> orderProducts =
        orderRequest.getOrderProducts().stream()
                .map(orderProductDto ->
                OrderProduct.createOrderProduct(
                        orderProductDto.getProductDto().toEntityWithId(), orderProductDto.getOrderPrice(), orderProductDto.getOrderQuantity()))
                .collect(Collectors.toList());

        Order order = Order.createOrder(member, delivery, orderProducts);
        Order createdOrder = orderRepository.save(order);

        return OrderDto.Response.of(createdOrder);
    }

    public OrderDto.Response getOrder(Long orderId) {
        Order order = findOrderById(orderId);
        return  OrderDto.Response.of(order);
    }

    public Page<OrderDto.Response> orderPages(Pageable page) {
        Page<Order> orderPage =  orderRepository.findAll(page);
        List<OrderDto.Response> ordersResponse = orderPage
                .getContent().stream()
                .map(OrderDto.Response::of)
                .collect(Collectors.toList());
        return new PageImpl<>(ordersResponse,page,orderPage.getTotalElements());
    }

    /** 주문 취소*/
    public OrderDto.Response cancelOrder(Long orderId) {
        Order order = findOrderById(orderId);
        order.cancel();
        return OrderDto.Response.of(order);
    }

    public Order findOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
    }

}
