package com.springreactshop.demo.service;

import com.springreactshop.demo.domain.Member;
import com.springreactshop.demo.repository.MemberRepository;
import com.springreactshop.demo.representation.JwtRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원추가() {
        //Given
        JwtRequest signupRequest = new JwtRequest();
        signupRequest.setUsername("testUser");
        signupRequest.setPassword("1234");

        //When
        String username = memberService.signUp(signupRequest);

        //Then
        Member signedMember = memberRepository.findByUsername(username);
        assertThat(signedMember).isNotNull();
        assertThat(signedMember.getUsername()).isEqualTo(signupRequest.getUsername());
    }
}