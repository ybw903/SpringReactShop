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
        List<OrderProductDto> orderProductDtos = new ArrayList<>();
        for(int i =1; i<4; i++) {
            OrderProductDto orderProductDto = generateProductRequest(i);
            productRepository.save(orderProductDto.getProductDto().toEntityWithId());
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
        OrderDto.Response order = orderService.order(orderRequest);

        //then
        assertThat(order.getMemberResponse().getUsername()).isEqualTo(member.getUsername());
        assertThat(order.getDelivery().getAddress().getPhone()).isEqualTo(address.getPhone());
        assertThat(order.getDelivery().getAddress().getStreet()).isEqualTo(address.getStreet());
        assertThat(order.getDelivery().getAddress().getZipcode()).isEqualTo(address.getZipcode());
        assertThat(order.getDelivery().getDeliveryStatus()).isEqualTo(DeliveryStatus.READY);
        assertThat(order.getOrderProducts().size()).isEqualTo(orderProductDtos.size());
        assertThat(order.getTotalPrice()).isEqualTo(
                        (Integer) orderProductDtos.stream()
                                .mapToInt(orderProductRequest -> orderProductRequest.getOrderPrice() * orderProductRequest.getOrderQuantity())
                                .sum());
        assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER);
    }

    @Test
    public void 주문취소() throws Exception{
        //given
        List<OrderProductDto> orderProductDtos = new ArrayList<>();
        for(int i =1; i<4; i++) {
            OrderProductDto orderProductDto = generateProductRequest(i);
            productRepository.save(orderProductDto.getProductDto().toEntityWithId());
            orderProductDtos.add(orderProductDto);
        }
        Member member = makeUser();
        Address address = new Address("000000", "서울시 강남구 테헤란로", "012-345-6789");
        OrderDto.Request orderRequest= OrderDto.Request.builder()
                .username(member.getUsername())
                .address(address)
                .orderProducts(orderProductDtos)
                .build();

        OrderDto.Response order =  orderService.order(orderRequest);

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
        MemberDto.InfoResponse member = memberService.getMemberProfileByUserName("user");
        MemberDto.AddressUpdateRequest memberUpdateAddressRequest = MemberDto.AddressUpdateRequest.builder()
                .phone("010-1234-5678")
                .zipcode("000000")
                .street("서울시강남구테헤란로")
                .build();

        memberService.updateMember(member.getUsername(), memberUpdateAddressRequest);
        return memberRepository.findByUsername("user").orElse(null);
    }

    public OrderProductDto generateProductRequest(int idx) {

        return OrderProductDto.builder()
                .productDto(
                        ProductDto.builder()
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