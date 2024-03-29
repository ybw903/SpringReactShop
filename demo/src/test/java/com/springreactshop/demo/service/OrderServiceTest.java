package com.springreactshop.demo.service;

import com.springreactshop.demo.domain.*;
import com.springreactshop.demo.dto.*;
import com.springreactshop.demo.repository.MemberRepository;
import com.springreactshop.demo.repository.OrderRepository;
import com.springreactshop.demo.repository.ProductRepository;
import com.springreactshop.demo.resource.MemberResource;
import com.springreactshop.demo.resource.OrderResource;
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
    void 주문하기() throws Exception{

        //given
        List<OrderProductDto> orderProductDtos = new ArrayList<>();
        for(int i =1; i<4; i++) {
            OrderProductDto orderProductDto = generateProductRequest(i);
            productRepository.save(orderProductDto.getProductInfo().toEntity());
            orderProductDtos.add(orderProductDto);
        }
        Member member = makeUser();
        Address address = new Address("000000", "서울시 강남구 테헤란로", "012-345-6789");
        OrderDto.Request orderRequest= OrderDto.Request.builder()
                                            .username(member.getUsername())
                                            .address(address)
                                            .payment("CREDIT")
                                            .orderProducts(orderProductDtos)
                                            .build();

        //when
        Order order = orderService.order(orderRequest);

        //then
        assertThat(order.getMember().getUsername()).isEqualTo(member.getUsername());
        assertThat(order.getDelivery().getAddress().getPhone()).isEqualTo(address.getPhone());
        assertThat(order.getDelivery().getAddress().getStreet()).isEqualTo(address.getStreet());
        assertThat(order.getDelivery().getAddress().getZipcode()).isEqualTo(address.getZipcode());
        assertThat(order.getDelivery().getDeliveryStatus()).isEqualTo(DeliveryStatus.READY);
        assertThat(order.getPayment()).isEqualTo(Payment.CREDIT);
        assertThat(order.getOrderProducts().size()).isEqualTo(orderProductDtos.size());
        assertThat(order.getTotalPrice()).isEqualTo(
                        (Integer) orderProductDtos.stream()
                                .mapToInt(orderProductRequest -> orderProductRequest.getOrderPrice() * orderProductRequest.getOrderQuantity())
                                .sum());
        assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER);
    }

    @Test
    void 지불방법없이_주문한_경우_에러발생() throws Exception{

        //given
        List<OrderProductDto> orderProductDtos = new ArrayList<>();
        for(int i =1; i<4; i++) {
            OrderProductDto orderProductDto = generateProductRequest(i);
            productRepository.save(orderProductDto.getProductInfo().toEntity());
            orderProductDtos.add(orderProductDto);
        }
        Member member = makeUser();
        Address address = new Address("000000", "서울시 강남구 테헤란로", "012-345-6789");
        OrderDto.Request orderRequest= OrderDto.Request.builder()
                .username(member.getUsername())
                .address(address)
                .orderProducts(orderProductDtos)
                .build();

        //when
        Order order = orderService.order(orderRequest);

        //then
        assertThat(order.getMember().getUsername()).isEqualTo(member.getUsername());
        assertThat(order.getDelivery().getAddress().getPhone()).isEqualTo(address.getPhone());
        assertThat(order.getDelivery().getAddress().getStreet()).isEqualTo(address.getStreet());
        assertThat(order.getDelivery().getAddress().getZipcode()).isEqualTo(address.getZipcode());
        assertThat(order.getDelivery().getDeliveryStatus()).isEqualTo(DeliveryStatus.READY);

        assertThat(order.getPayment()).isNull(); //TODO: validate 추가

        assertThat(order.getOrderProducts().size()).isEqualTo(orderProductDtos.size());
        assertThat(order.getTotalPrice()).isEqualTo(
                (Integer) orderProductDtos.stream()
                        .mapToInt(orderProductRequest -> orderProductRequest.getOrderPrice() * orderProductRequest.getOrderQuantity())
                        .sum());
        assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER);
    }


    @Test
    void 주문취소() throws Exception{
        //given
        List<OrderProductDto> orderProductDtos = new ArrayList<>();
        for(int i =1; i<4; i++) {
            OrderProductDto orderProductDto = generateProductRequest(i);
            productRepository.save(orderProductDto.getProductInfo().toEntity());
            orderProductDtos.add(orderProductDto);
        }
        Member member = makeUser();
        Address address = new Address("000000", "서울시 강남구 테헤란로", "012-345-6789");
        OrderDto.Request orderRequest= OrderDto.Request.builder()
                .username(member.getUsername())
                .address(address)
                .orderProducts(orderProductDtos)
                .build();

        Order order =  orderService.order(orderRequest);

        //when
        orderService.cancelOrder(order.getId());

        //then
        Order cancelOrder = orderRepository.findById(order.getId()).get();
        assertThat(cancelOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
    }

    @Test
    void 주문목록() {
        //given

    }

    private Member makeUser() {
        AuthDto.Request signupRequest = new AuthDto.Request("user","password");

        memberService.signUpUser(signupRequest);
        Member member = memberService.getMemberProfileByUserName("user");
        MemberDto.AddressUpdateRequest memberUpdateAddressRequest = MemberDto.AddressUpdateRequest.builder()
                .phone("010-1234-5678")
                .zipcode("000000")
                .street("서울시강남구테헤란로")
                .build();

        memberService.updateMember(member.getUsername(), memberUpdateAddressRequest);
        return memberRepository.findByUsername("user").orElse(null);
    }

    private OrderProductDto generateProductRequest(int idx) {

        return OrderProductDto.builder()
                .productInfo(
                        ProductDto.Info.builder()
                                .id((long)idx)
                                .productName("test" + idx)
                                .productDescription("no")
                                .productPrice(idx*100)
                                .productQuantity(999)
                                .build()
                )
                .orderPrice(idx*100)
                .orderQuantity(1)
                .build();
    }

}