package com.springreactshop.demo.service;

import com.springreactshop.demo.configuration.JwtTokenUtil;
import com.springreactshop.demo.domain.*;
import com.springreactshop.demo.repository.MemberRepository;
import com.springreactshop.demo.repository.OrderRepository;
import com.springreactshop.demo.repository.ProductRepository;
import com.springreactshop.demo.representation.JwtRequest;
import com.springreactshop.demo.representation.MemberDto;
import com.springreactshop.demo.representation.OrderRequest;
import com.springreactshop.demo.representation.ProductRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    public void 주문하기() {

        //given
        List<ProductRequest> productRequests = new ArrayList<>();
        for(int i =1; i<4; i++) {
            ProductRequest productRequest = generateProductRequest(i);
            productRepository.save(productRequest.toEntity());
            productRequests.add(productRequest);
        }
        Member member = makeUser();
        Address address = new Address("000000", "서울시 강남구 테헤란로", "012-345-6789");
        OrderRequest orderRequest = new OrderRequest(member.getUsername(),address, productRequests);

        //when
        Order order = orderService.order(orderRequest);

        //then
        assertThat(order.getMember().getUsername()).isEqualTo(member.getUsername());
        assertThat(order.getDelivery().getAddress().getPhone()).isEqualTo(member.getAddress().getPhone());
        assertThat(order.getDelivery().getAddress().getStreet()).isEqualTo(member.getAddress().getStreet());
        assertThat(order.getDelivery().getAddress().getZipcode()).isEqualTo(member.getAddress().getZipcode());
        assertThat(order.getDelivery().getDeliveryStatus()).isEqualTo(DeliveryStatus.READY);
        assertThat(order.getOrderProducts().size()).isEqualTo(productRequests.size());
        assertThat(order.getTotalPrice()).isEqualTo(
                        (Integer) productRequests.stream()
                                .mapToInt(productRequest -> productRequest.getOrderPrice() * productRequest.getOrderQuantity())
                                .sum());
        assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER);
    }

    @Test
    public void 주문취소() {
        //given
        List<ProductRequest> productRequests = new ArrayList<>();
        for(int i =1; i<4; i++) {
            ProductRequest productRequest = generateProductRequest(i);
            productRepository.save(productRequest.toEntity());
            productRequests.add(productRequest);
        }
        Member member = makeUser();
        Address address = new Address("000000", "서울시 강남구 테헤란로", "012-345-6789");
        OrderRequest orderRequest = new OrderRequest(member.getUsername(),address, productRequests);
        Order order =  orderService.order(orderRequest);

        //when
        orderService.cancelOrder(order.getId());

        //then
        Order cancelOrder = orderRepository.findById(order.getId()).get();
        assertThat(cancelOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
    }

    @Test
    public void 주문목록() {
        //given
        List<ProductRequest> productRequests = new ArrayList<>();
        for(int i =1; i<4; i++) {
            ProductRequest productRequest = generateProductRequest(i);
            productRepository.save(productRequest.toEntity());
            productRequests.add(productRequest);
        }
        Member member = makeUser();
        Address address = new Address("000000", "서울시 강남구 테헤란로", "012-345-6789");
        OrderRequest orderRequest = new OrderRequest(member.getUsername(),address, productRequests);
        Order order =  orderService.order(orderRequest);

        //when
        orderService.cancelOrder(order.getId());

        //then
        Order cancelOrder = orderRepository.findById(order.getId()).get();
        assertThat(cancelOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
    }

    public Member makeUser() {
        JwtRequest signupRequest = new JwtRequest();
        signupRequest.setUsername("user");
        signupRequest.setPassword("password");

        memberService.signUp(signupRequest);
        Member member = memberService.getUserProfileByUserName("user");
        MemberDto memberDto = MemberDto.builder()
                .phone("010-1234-5678")
                .zipcode("000000")
                .street("서울시강남구테헤란로")
                .build();

        memberService.updateMember(member,memberDto);
        return memberRepository.findByUsername("user");
    }

    public ProductRequest generateProductRequest(int idx) {

        return ProductRequest.builder()
                .id((long) idx)
                .productName("test"+idx)
                .productDescription("no")
                .productPrice(idx*100)
                .productQuantity(999)
                .orderPrice(idx*100)
                .orderQuantity(1)
                .build();
    }

}