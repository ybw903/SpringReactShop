package com.springreactshop.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springreactshop.demo.configuration.JwtTokenUtil;
import com.springreactshop.demo.domain.Address;
import com.springreactshop.demo.domain.OrderStatus;
import com.springreactshop.demo.repository.ProductRepository;
import com.springreactshop.demo.representation.JwtRequest;
import com.springreactshop.demo.representation.OrderRequest;
import com.springreactshop.demo.representation.ProductRequest;
import com.springreactshop.demo.service.JwtUserDetailService;
import com.springreactshop.demo.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
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
    private JwtUserDetailService jwtUserDetailService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void 주문추가() throws Exception {
        //given
        JwtRequest signRequest = new JwtRequest();
        signRequest.setUsername("testUser");
        signRequest.setPassword("1234");
        String username = jwtUserDetailService.addUser(signRequest);
        UserDetails userDetails = jwtUserDetailService.loadUserByUsername(username);
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
                .andExpect(jsonPath("orderDate").exists());
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