package com.springreactshop.demo.service;

import com.springreactshop.demo.domain.*;
import com.springreactshop.demo.dto.*;
import com.springreactshop.demo.repository.MemberRepository;
import com.springreactshop.demo.repository.OrderRepository;
import com.springreactshop.demo.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
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
    public void 주문하기() throws Exception{

        //given
        List<OrderProductRequest> orderProductRequests = new ArrayList<>();
        for(int i =1; i<4; i++) {
            OrderProductRequest orderProductRequest = generateProductRequest(i);
            productRepository.save(orderProductRequest.toEntity());
            orderProductRequests.add(orderProductRequest);
        }
        Member member = makeUser();
        Address address = new Address("000000", "서울시 강남구 테헤란로", "012-345-6789");
        OrderRequest orderRequest = new OrderRequest(member.getUsername(),address, orderProductRequests);

        //when
        Order order = orderService.order(orderRequest);

        //then
        assertThat(order.getMember().getUsername()).isEqualTo(member.getUsername());
        assertThat(order.getDelivery().getAddress().getPhone()).isEqualTo(address.getPhone());
        assertThat(order.getDelivery().getAddress().getStreet()).isEqualTo(address.getStreet());
        assertThat(order.getDelivery().getAddress().getZipcode()).isEqualTo(address.getZipcode());
        assertThat(order.getDelivery().getDeliveryStatus()).isEqualTo(DeliveryStatus.READY);
        assertThat(order.getOrderProducts().size()).isEqualTo(orderProductRequests.size());
        assertThat(order.getTotalPrice()).isEqualTo(
                        (Integer) orderProductRequests.stream()
                                .mapToInt(orderProductRequest -> orderProductRequest.getOrderPrice() * orderProductRequest.getOrderQuantity())
                                .sum());
        assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER);
    }

    @Test
    public void 주문취소() throws Exception{
        //given
        List<OrderProductRequest> orderProductRequests = new ArrayList<>();
        for(int i =1; i<4; i++) {
            OrderProductRequest orderProductRequest = generateProductRequest(i);
            productRepository.save(orderProductRequest.toEntity());
            orderProductRequests.add(orderProductRequest);
        }
        Member member = makeUser();
        Address address = new Address("000000", "서울시 강남구 테헤란로", "012-345-6789");
        OrderRequest orderRequest = new OrderRequest(member.getUsername(),address, orderProductRequests);
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

    }

    public Member makeUser() {
        AuthDto.Request signupRequest = new AuthDto.Request("user","password");

        memberService.signUpUser(signupRequest);
        Member member = memberService.getMemberProfileByUserName("user");
        MemberDto.AddressUpdateRequest memberUpdateAddressRequest = MemberDto.AddressUpdateRequest.builder()
                .phone("010-1234-5678")
                .zipcode("000000")
                .street("서울시강남구테헤란로")
                .build();

        memberService.updateMember(member, memberUpdateAddressRequest);
        return memberRepository.findByUsername("user").orElse(null);
    }

    public OrderProductRequest generateProductRequest(int idx) {

        return OrderProductRequest.builder()
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