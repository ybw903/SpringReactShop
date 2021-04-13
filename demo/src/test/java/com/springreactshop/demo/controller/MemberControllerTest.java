package com.springreactshop.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springreactshop.demo.config.RestDocsConfiguration;
import com.springreactshop.demo.configuration.JwtTokenUtil;
import com.springreactshop.demo.domain.Member;
import com.springreactshop.demo.domain.MemberRole;
import com.springreactshop.demo.repository.MemberRepository;
import com.springreactshop.demo.repository.OrderRepository;
import com.springreactshop.demo.repository.ProductRepository;
import com.springreactshop.demo.representation.MemberUpdateAddressRequest;
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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtMemberDetailService jwtMemberDetailService;

    @Autowired
    private MemberRepository memberRepository;


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
    public void 회원정보조회() throws Exception {
        //given
        UserDetails userDetails = jwtMemberDetailService.loadUserByUsername("testUser");
        String token = jwtTokenUtil.generateToken(userDetails);

        //when&then
        this.mockMvc.perform(get("/api/members/{username}","testUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .accept(MediaTypes.HAL_JSON))
                        .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(header().string("Content-Type", "application/hal+json"))
                    .andExpect(jsonPath("username").exists())
                    .andExpect(jsonPath("zipcode").exists())
                    .andExpect(jsonPath("street").exists())
                    .andExpect(jsonPath("phone").exists())
                    .andDo(document("member/member-profile",
                            links(
                                    linkWithRel("self").description("link to self")
                            ),
                            requestHeaders(
                                    headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                    headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type header"),
                                    headerWithName(HttpHeaders.AUTHORIZATION).description("jwt Authorization header")
                            ),
                            pathParameters(
                                    parameterWithName("username").description("username of member")
                            ),
                            responseHeaders(
                                    headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type")
                            ),
                            responseFields(
                                    fieldWithPath("username").description("username of member"),
                                    fieldWithPath("zipcode").description("zipcode of member"),
                                    fieldWithPath("street").description("street of member"),
                                    fieldWithPath("phone").description("phone of member"),
                                    fieldWithPath("_links.self.href").description("link to self")
                            )
                    ));

    }

    @Test
    public void 회원정보수정() throws Exception {
        //given
        UserDetails userDetails = jwtMemberDetailService.loadUserByUsername("testUser");
        String token = jwtTokenUtil.generateToken(userDetails);
        MemberUpdateAddressRequest memberUpdateAddressRequest
                = new MemberUpdateAddressRequest("000000","서울시강남구테헤란로","012-345-6789");

        //when&then
        this.mockMvc.perform(put("/api/members/{username}","testUser")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(memberUpdateAddressRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/hal+json"))
                .andExpect(jsonPath("username").exists())
                .andExpect(jsonPath("zipcode").exists())
                .andExpect(jsonPath("street").exists())
                .andExpect(jsonPath("phone").exists())
                .andDo(document("member/member-update",
                        links(
                                linkWithRel("self").description("link to self")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type header"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("jwt Authorization header")
                        ),
                        pathParameters(
                                parameterWithName("username").description("username of member")
                        ),
                        requestFields(
                                fieldWithPath("zipcode").description("zipcode to update for member"),
                                fieldWithPath("street").description("street to update for member"),
                                fieldWithPath("phone").description("phone to update for member")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type")
                        ),
                        responseFields(
                                fieldWithPath("username").description("username of member"),
                                fieldWithPath("zipcode").description("zipcode of member"),
                                fieldWithPath("street").description("street of member"),
                                fieldWithPath("phone").description("phone of member"),
                                fieldWithPath("_links.self.href").description("link to self")
                        )
                ));

    }
}