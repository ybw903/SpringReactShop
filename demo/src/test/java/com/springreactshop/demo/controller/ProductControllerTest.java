package com.springreactshop.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springreactshop.demo.config.RestDocsConfiguration;
import com.springreactshop.demo.configuration.JwtTokenUtil;
import com.springreactshop.demo.domain.Member;
import com.springreactshop.demo.domain.MemberRole;
import com.springreactshop.demo.domain.Product;
import com.springreactshop.demo.repository.MemberRepository;
import com.springreactshop.demo.repository.ProductRepository;
import com.springreactshop.demo.representation.ProductDto;
import com.springreactshop.demo.representation.ProductRequest;
import com.springreactshop.demo.service.JwtMemberDetailService;
import org.junit.jupiter.api.AfterEach;
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
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    JwtMemberDetailService jwtMemberDetailService;

    @AfterEach
    void 멤버저장소초기화() {
        memberRepository.deleteAll();
    }

    @Test
    void 상품목록조회테스트() throws Exception {
        //given
        Product product1 =
                ProductRequest.builder()
                        .id(1L)
                        .productName("test1")
                        .productDescription("test1")
                        .productPrice(100)
                        .productQuantity(999)
                        .build().toEntity();
        Product product2 =
                ProductRequest.builder()
                        .id(2L)
                        .productName("test2")
                        .productDescription("test2")
                        .productPrice(200)
                        .productQuantity(999)
                        .build().toEntity();
        Product product3 =
                ProductRequest.builder()
                        .id(3L)
                        .productName("test3")
                        .productDescription("test3")
                        .productPrice(300)
                        .productQuantity(999)
                        .build().toEntity();
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        //when
        ResultActions resultActions = this.mockMvc.perform(get("/api/products?page=0&size=10")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaTypes.HAL_JSON));

        //then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/hal+json"))
                .andExpect(jsonPath("_embedded").exists())
                .andExpect(jsonPath("_embedded.productResources").exists())
                .andExpect(jsonPath("_embedded.productResources[0].id").exists())
                .andExpect(jsonPath("_embedded.productResources[0].productName").exists())
                .andExpect(jsonPath("_embedded.productResources[0].productDescription").exists())
                .andExpect(jsonPath("_embedded.productResources[0].productPrice").exists())
                .andExpect(jsonPath("_embedded.productResources[0].productQuantity").exists())
                .andExpect(jsonPath("_embedded.productResources[0]._links.self").exists())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andDo(document("product/product-list",
                        links(
                                linkWithRel("self").description("lintk to self")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type header")
                        ),
                        requestParameters(
                                parameterWithName("page").description("The page to retrieve"),
                                parameterWithName("size").description("size of page per element")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type")
                        ),
                        responseFields(
                                subsectionWithPath("_embedded").description("It is an object link relation types"),
                                subsectionWithPath("_embedded.productResources").description("productResource List"),
                                subsectionWithPath("page").description("query of pagable"),
                                fieldWithPath("_links.self.href").description("link to self")
                        )
                ));
    }

    @Test
    void 상품추가() throws Exception {

        //given
        Member member = Member.builder()
                .username("adminUser")
                .password("1234")
                .memberRole(MemberRole.ADMIN)
                .build();
        memberRepository.save(member);

        UserDetails userDetails = jwtMemberDetailService.loadUserByUsername("adminUser");
        String token = jwtTokenUtil.generateToken(userDetails);

        ProductDto productDto = ProductDto
                .builder()
                .productName("testProduct")
                .productDescription("testProduct")
                .productPrice(100)
                .productQuantity(999)
                .build();

        //when
        ResultActions resultActions = this.mockMvc.perform(post("/api/products?page=0&size=10")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .header("Authorization","Bearer " + token)
                .content(objectMapper.writeValueAsString(productDto))
        );

        //then
        resultActions
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string("Content-Type", "application/hal+json"))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("productName").exists())
                .andExpect(jsonPath("productDescription").exists())
                .andExpect(jsonPath("productPrice").exists())
                .andExpect(jsonPath("productQuantity").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.productsList").exists())
                .andExpect(jsonPath("_links.update-product").exists())
                .andDo(document("product/create-product",
                        links(
                                linkWithRel("self").description("lintk to self"),
                                linkWithRel("productsList").description("lintk to productsList"),
                                linkWithRel("update-product").description("lintk to update an existing product")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type header"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("jwt Authorization header")
                        ),
                        requestFields(
                                fieldWithPath("productName").description("name of new product"),
                                fieldWithPath("productDescription").description("description of new product"),
                                fieldWithPath("productPrice").description("price of new product"),
                                fieldWithPath("productQuantity").description("quantity of new product")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Content type"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type")
                        ),
                        responseFields(
                                fieldWithPath("id").description("identifier of new product"),
                                fieldWithPath("productName").description("name of new product"),
                                fieldWithPath("productDescription").description("description of new product"),
                                fieldWithPath("productPrice").description("price of new product"),
                                fieldWithPath("productQuantity").description("quantity of new product"),
                                fieldWithPath("_links.self.href").description("link to self"),
                                fieldWithPath("_links.productsList.href").description("link to productsList"),
                                fieldWithPath("_links.update-product.href").description("link to update an existing product")
                        )
                ));
    }

    @Test
    void 상품수정() throws Exception {

        //given
        Member member = Member.builder()
                .username("adminUser")
                .password("1234")
                .memberRole(MemberRole.ADMIN)
                .build();
        memberRepository.save(member);

        UserDetails userDetails = jwtMemberDetailService.loadUserByUsername("adminUser");
        String token = jwtTokenUtil.generateToken(userDetails);

        Product product = ProductDto
                .builder()
                .productName("testProduct")
                .productDescription("testProduct")
                .productPrice(100)
                .productQuantity(999)
                .build().toEntity();
        Product savedProduct = productRepository.save(product);

        ProductDto productDto = ProductDto
                .builder()
                .productName("changedProduct")
                .productDescription("changedProductDescription")
                .productPrice(999)
                .productQuantity(9999)
                .build();

        //when
        ResultActions resultActions = this.mockMvc.perform(put("/api/products/{id}",product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .header("Authorization","Bearer " + token)
                .content(objectMapper.writeValueAsString(productDto))
        );

        //then
        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type","application/hal+json"))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("productName").exists())
                .andExpect(jsonPath("productDescription").exists())
                .andExpect(jsonPath("productPrice").exists())
                .andExpect(jsonPath("productQuantity").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.productsList").exists())
                .andExpect(jsonPath("_links.update-product").exists())
                .andDo(document("product/update-product",
                        links(
                                linkWithRel("self").description("lintk to self"),
                                linkWithRel("productsList").description("lintk to productsList"),
                                linkWithRel("update-product").description("lintk to update an existing product")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type header"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("jwt Authorization header")
                        ),
                        pathParameters(
                                parameterWithName("id").description("identifier an existing product")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type")
                        ),
                        responseFields(
                                fieldWithPath("id").description("identifier of changed product"),
                                fieldWithPath("productName").description("name of changed product"),
                                fieldWithPath("productDescription").description("description of changed product"),
                                fieldWithPath("productPrice").description("price of changed product"),
                                fieldWithPath("productQuantity").description("quantity of changed product"),
                                fieldWithPath("_links.self.href").description("link to self"),
                                fieldWithPath("_links.productsList.href").description("link to productsList"),
                                fieldWithPath("_links.update-product.href").description("link to update an existing product")
                        )
                ));
    }

    @Test
    void 상품삭제() throws Exception {

        //given
        Member member = Member.builder()
                .username("adminUser")
                .password("1234")
                .memberRole(MemberRole.ADMIN)
                .build();
        memberRepository.save(member);

        UserDetails userDetails = jwtMemberDetailService.loadUserByUsername("adminUser");
        String token = jwtTokenUtil.generateToken(userDetails);

        Product product = ProductDto
                .builder()
                .productName("testProduct")
                .productDescription("testProduct")
                .productPrice(100)
                .productQuantity(999)
                .build().toEntity();
        Product savedProduct = productRepository.save(product);


        //when
        ResultActions resultActions = this.mockMvc.perform(delete("/api/products/{id}",product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .header("Authorization","Bearer " + token)
        );

        //then
        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("product/delete-product",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type header"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("jwt Authorization header")
                        ),
                        pathParameters(
                                parameterWithName("id").description("identifier an existing product")
                        )
                ));
    }



}