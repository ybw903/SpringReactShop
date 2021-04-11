package com.springreactshop.demo.service;

import com.springreactshop.demo.domain.*;
import com.springreactshop.demo.repository.MemberRepository;
import com.springreactshop.demo.repository.OrderRepository;
import com.springreactshop.demo.representation.OrderRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class OrderService {

    private final MemberRepository memberRepository;

    private final OrderRepository orderRepository;

    public Order order(OrderRequest orderRequest) {
        Member member =  memberRepository.findByUsername(orderRequest.getUsername());
        Delivery delivery = Delivery.createDelivery(orderRequest.getAddress());

        List<OrderProduct> orderProducts =
        orderRequest.getProductList().stream()
                .map(productRequest ->
                OrderProduct.createOrderProduct(productRequest.toEntity(), productRequest.getOrderPrice(), productRequest.getOrderQuantity()))
                .collect(Collectors.toList());

        Order order = Order.createOrder(member, delivery, orderProducts);
        orderRepository.save(order);

        return  order;
    }

    public Optional<Order> getOrder(Long orderId) {
        return orderRepository.findById(orderId);
    }

    public Page<Order> orderPages(Pageable page) {

        return orderRepository.findAll(page);
    }

    /** 주문 취소*/
    public Order cancelOrder(Long orderId) {
        Optional<Order> optionalOrder =orderRepository.findById(orderId);
        Order order = optionalOrder.get();
        order.cancel();
        return orderRepository.save(order);
    }

}
