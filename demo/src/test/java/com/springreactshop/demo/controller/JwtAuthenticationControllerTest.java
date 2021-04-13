package com.springreactshop.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springreactshop.demo.config.RestDocsConfiguration;
import com.springreactshop.demo.domain.Member;
import com.springreactshop.demo.domain.MemberRole;
import com.springreactshop.demo.repository.MemberRepository;
import com.springreactshop.demo.representation.JwtRequest;
import com.springreactshop.demo.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
class JwtAuthenticationControllerTest {


    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void 인증토큰() throws Exception {
        //given
        Member member = Member.builder()
                .username("testUserA")
                .password(passwordEncoder.encode("1234"))
                .memberRole(MemberRole.USER)
                .build();
        memberRepository.save(member);

        JwtRequest signRequest = new JwtRequest();
        signRequest.setUsername("testUserA");
        signRequest.setPassword("1234");

        //when&then
        this.mockMvc.perform(post("/api/authenticate/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signRequest))
                        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("jwttoken").exists())
                .andDo(document("login",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type header")
                        ),
                        requestFields(
                                fieldWithPath("username").description("member name requiring authentication"),
                                fieldWithPath("password").description("member password requiring authentication")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type")
                        ),
                        responseFields(
                                fieldWithPath("jwttoken").description("signed jwttoken")
                        )
                ))

        ;
    }
}