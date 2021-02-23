package com.springreactshop.demo.service;

import com.springreactshop.demo.domain.Member;
import com.springreactshop.demo.repository.MemberRepository;
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
        Member member = new Member();
        member.setUserName("testUser");

        //When
        Long saveId = memberService.signUp(member);

        //Then
        Member signedMember = memberRepository.findAll().get(0);
        assertThat(signedMember.getUserName()).isEqualTo(member.getUserName());
    }
}