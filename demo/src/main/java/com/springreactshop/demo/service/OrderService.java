package com.springreactshop.demo.service;

import com.springreactshop.demo.domain.*;
import com.springreactshop.demo.dto.OrderDto;
import com.springreactshop.demo.exception.OrderNotFoundException;
import com.springreactshop.demo.repository.OrderRepository;
import com.springreactshop.demo.resource.OrderResource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class OrderService {

    private final MemberService memberService;

    private final OrderRepository orderRepository;

    public Order order(OrderDto.Request orderRequest) {
        Member member = memberService.findMemberByUsername(orderRequest.getUsername());
        Delivery delivery = Delivery.createDelivery(orderRequest.getAddress());

        List<OrderProduct> orderProducts =
        orderRequest.getOrderProducts().stream()
                .map(orderProductDto ->
                OrderProduct.createOrderProduct(
                        orderProductDto.getProductInfo().toEntity(), orderProductDto.getOrderPrice(), orderProductDto.getOrderQuantity()))
                .collect(Collectors.toList());

        Order order = Order.createOrder(member, delivery, orderProducts);
        Order createdOrder = orderRepository.save(order);

        return createdOrder;
    }

    public Order getOrder(Long orderId) {
        return findOrderById(orderId);
    }

    public Page<Order> orderPages(Pageable page) {
        Page<Order> orderPage =  orderRepository.findAll(page);
        return orderPage;
    }

    /** 주문 취소*/
    public Order cancelOrder(Long orderId) {
        Order order = findOrderById(orderId);
        order.cancel();
        return order;
    }

    public Order findOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
    }

}
