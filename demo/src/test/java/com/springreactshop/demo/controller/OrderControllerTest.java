package com.springreactshop.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springreactshop.demo.config.RestDocsConfiguration;
import com.springreactshop.demo.configuration.JwtTokenUtil;
import com.springreactshop.demo.domain.*;
import com.springreactshop.demo.repository.MemberRepository;
import com.springreactshop.demo.repository.OrderRepository;
import com.springreactshop.demo.repository.ProductRepository;
import com.springreactshop.demo.representation.OrderRequest;
import com.springreactshop.demo.representation.ProductRequest;
import com.springreactshop.demo.service.JwtMemberDetailService;
import com.springreactshop.demo.service.OrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtMemberDetailService jwtMemberDetailService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void 회원설정() {
        Member member = Member.builder()
                .username("testUser")
                .password("1234")
                .memberRole(MemberRole.USER)
                .build();
        memberRepository.save(member);
    }

    @AfterEach
    public void 레포지토리초기화() {
        memberRepository.deleteAll();
    }

    @Test
    public void 주문추가() throws Exception {
        //given
        UserDetails userDetails = jwtMemberDetailService.loadUserByUsername("testUser");
        String token = jwtTokenUtil.generateToken(userDetails);
        Address address = new Address("000000","서울시강남구테헤란로", "012-345-6789");

        List<ProductRequest> productRequests = new ArrayList<>();
        for(int i =1; i<4; i++) {
            ProductRequest productRequest = generateProductRequest(i);
            productRepository.save(productRequest.toEntity());
            productRequests.add(productRequest);
        }

        OrderRequest orderRequest = new OrderRequest(userDetails.getUsername(),address, productRequests);

        //when&then
        this.mockMvc.perform(post("/api/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + token)
                    .accept(MediaTypes.HAL_JSON)
                    .content(objectMapper.writeValueAsString(orderRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string("Content-Type", "application/hal+json"))
                .andExpect(jsonPath("member").exists())
                .andExpect(jsonPath("delivery").exists())
                .andExpect(jsonPath("status").value(OrderStatus.ORDER.name()))
                .andExpect(jsonPath("totalPrice").exists())
                .andExpect(jsonPath("orderDate").exists())
                .andDo(document("order/create-order",
                        links(
                                linkWithRel("self").description("link to self"),
                                linkWithRel("cancel-order").description("link to cancel an existing order")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type header"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("jwt Authorization header")
                        ),
                        requestFields(
                                fieldWithPath("username").description("Name of new order"),
                                subsectionWithPath("address").description("address of new order"),
                                subsectionWithPath("productList").description("productList of new order")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type")
                        ),
                        responseFields(
                                fieldWithPath("id").description("identifier of new order"),
                                subsectionWithPath("member").description("member of new order"),
                                subsectionWithPath("delivery").description("delivery of new order"),
                                subsectionWithPath("orderProducts").description("orderProducts of new order"),
                                fieldWithPath("status").description("status of new order"),
                                fieldWithPath("totalPrice").description("totalPrice of new order"),
                                fieldWithPath("orderDate").description("orderDate of new order"),
                                fieldWithPath("_links.self.href").description("link to self"),
                                fieldWithPath("_links.cancel-order.href").description("link to cancel an existing order")
                        )

                 ));
    }


    @Test
    public void 주문취소() throws Exception {
        //given
        UserDetails userDetails = jwtMemberDetailService.loadUserByUsername("testUser");
        String token = jwtTokenUtil.generateToken(userDetails);

        Address address = new Address("000000","서울시강남구테헤란로", "012-345-6789");
        List<ProductRequest> productRequests = new ArrayList<>();
        for(int i =1; i<4; i++) {
            ProductRequest productRequest = generateProductRequest(i);
            productRepository.save(productRequest.toEntity());
            productRequests.add(productRequest);
        }

        OrderRequest orderRequest = new OrderRequest("testUser",address,productRequests);
        Order addedOrder = orderService.order(orderRequest);
        Long orderId = addedOrder.getId();

        //when&then
        this.mockMvc.perform(delete("/api/orders/{orderId}",orderId)
                //java.lang.IllegalArgumentException: urlTemplate not found. : MockMVCRequestBuilder =>RestDocumentationRequestBuilders (https://java.ihoney.pe.kr/517)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().string("Content-Type", "application/hal+json"))
                .andExpect(jsonPath("member").exists())
                .andExpect(jsonPath("delivery").exists())
                .andExpect(jsonPath("status").value(OrderStatus.CANCEL.name()))
                .andExpect(jsonPath("totalPrice").exists())
                .andExpect(jsonPath("orderDate").exists())
                .andDo(document("order/cancel-order",
                        links(
                                linkWithRel("self").description("link to self")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type header"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("jwt Authorization header")
                        ),
                        pathParameters(
                                parameterWithName("orderId").description("identifier of order to cancel")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type")
                        ),
                        responseFields(
                                fieldWithPath("id").description("identifier of new order"),
                                subsectionWithPath("member").description("member of new order"),
                                subsectionWithPath("delivery").description("delivery of new order"),
                                subsectionWithPath("orderProducts").description("orderProducts of new order"),
                                fieldWithPath("status").description("status of new order"),
                                fieldWithPath("totalPrice").description("totalPrice of new order"),
                                fieldWithPath("orderDate").description("orderDate of new order"),
                                fieldWithPath("_links.self.href").description("link to self")
                        )

                ));
    }

    @Test
    public void 주문페이지() throws Exception {
        //given
        UserDetails userDetails = jwtMemberDetailService.loadUserByUsername("testUser");
        String token = jwtTokenUtil.generateToken(userDetails);
        Address address = new Address("000000","서울시강남구테헤란로", "012-345-6789");

        List<ProductRequest> productRequests = new ArrayList<>();
        for(int i =1; i<10; i++) {
            ProductRequest productRequest = generateProductRequest(i);
            productRepository.save(productRequest.toEntity());
            productRequests.add(productRequest);
        }

        OrderRequest orderRequest1 = new OrderRequest("testUser",address,productRequests.subList(0,3));
        OrderRequest orderRequest2 = new OrderRequest("testUser",address,productRequests.subList(4,6));
        OrderRequest orderRequest3 = new OrderRequest("testUser",address,productRequests.subList(7,9));
        Order addedOrder1 = orderService.order(orderRequest1);
        Order addedOrder2 = orderService.order(orderRequest2);
        Order addedOrder3 = orderService.order(orderRequest3);

        //when&then
        this.mockMvc.perform(get("/api/orders?page=0&size=10&sort=orderDate,asc")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + token)
                            .accept(MediaTypes.HAL_JSON))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(header().string("Content-Type", "application/hal+json"))
                        .andExpect(jsonPath("page").exists())
                        .andExpect(jsonPath("_links.self").exists())
                        .andExpect(jsonPath("_embedded.orderResources").exists())
                        .andExpect(jsonPath("_embedded.orderResources[0]._links.self").exists())
                        .andDo(document("order/order-list",
                                links(
                                        linkWithRel("self").description("link to self")
                                ),
                                requestHeaders(
                                        headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                        headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type header"),
                                        headerWithName(HttpHeaders.AUTHORIZATION).description("jwt Authorization header")
                                ),
                                requestParameters(
                                        parameterWithName("page").description("The page to retrieve"),
                                        parameterWithName("size").description("size of page per element"),
                                        parameterWithName("sort").description("sort of page")
                                ),
                                responseHeaders(
                                        headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type")
                                ),
                                responseFields(
                                        subsectionWithPath("_embedded").description("It is an object link relation types"),
                                        subsectionWithPath("_embedded.orderResources").description("orderResource List"),
                                        subsectionWithPath("page").description("query of pagable"),
                                        fieldWithPath("_links.self.href").description("link to self")
                                )

                        ));
        ;

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