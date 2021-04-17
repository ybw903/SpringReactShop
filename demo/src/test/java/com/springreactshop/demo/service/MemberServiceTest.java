package com.springreactshop.demo.service;

import com.springreactshop.demo.domain.Member;
import com.springreactshop.demo.dto.AuthDto;
import com.springreactshop.demo.dto.MemberDto;
import com.springreactshop.demo.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;


    static AuthDto.Request signupRequest;
    @BeforeAll
    static void 준비() {
        signupRequest = new AuthDto.Request("testUserC", "1234");
    }
    @AfterEach
    void 멤버저장소초기화() {
        memberRepository.deleteAll();
    }

    @Test
    public void 회원추가() {

        //When
        String username = memberService.signUpUser(signupRequest);

        //Then
        assertThat(username).isEqualTo("testUserC");
    }

    @Test
    public void 회원주소정보갱신() throws Exception{

        String username = memberService.signUpUser(signupRequest);
        Member member = memberRepository.findByUsername("testUserC").orElseThrow();
        MemberDto.AddressUpdateRequest memberUpdateAddressRequest
                = new MemberDto.AddressUpdateRequest("000000","서울시강남구테헤란로","012-345-6789");

        //When
        MemberDto.InfoResponse updatedMember = memberService.updateMember(username,memberUpdateAddressRequest);

        //Then
        assertThat(updatedMember.getUsername()).isEqualTo("testUserC");
        assertThat(updatedMember.getZipcode()).isEqualTo("000000");
        assertThat(updatedMember.getStreet()).isEqualTo("서울시강남구테헤란로");
        assertThat(updatedMember.getPhone()).isEqualTo("012-345-6789");
    }

    @Test
    public void 회원정보조회() throws Exception{
        //Given
        String username = memberService.signUpUser(signupRequest);

        //When
        MemberDto.InfoResponse memberProfile = memberService.getMemberProfileByUserName(username);

        //Then
        assertThat(memberProfile.getUsername()).isEqualTo(signupRequest.getUsername());
    }
}