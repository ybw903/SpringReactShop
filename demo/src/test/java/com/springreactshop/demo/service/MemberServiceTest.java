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
    public void 회원가입() {
        //Given
        JwtRequest signupRequest = new JwtRequest();
        signupRequest.setUsername("testUser");
        signupRequest.setPassword("1234");

        //When
        Long saveId = memberService.signUp(signupRequest);

        //Then
        Member signedMember = memberRepository.findById(saveId).orElse(null);
        assertThat(signedMember).isNotNull();
        assertThat(signedMember.getUsername()).isEqualTo(signupRequest.getUsername());
    }
}