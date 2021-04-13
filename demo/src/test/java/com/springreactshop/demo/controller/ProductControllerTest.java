package com.springreactshop.demo.controller;

import com.springreactshop.demo.config.RestDocsConfiguration;
import com.springreactshop.demo.domain.Member;
import com.springreactshop.demo.domain.MemberRole;
import com.springreactshop.demo.domain.Product;
import com.springreactshop.demo.repository.ProductRepository;
import com.springreactshop.demo.representation.ProductRequest;
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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
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
    ProductRepository productRepository;

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

}