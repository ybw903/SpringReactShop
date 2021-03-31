package com.springreactshop.demo.service;

import com.springreactshop.demo.domain.Member;
import com.springreactshop.demo.representation.JwtRequest;
import com.springreactshop.demo.representation.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtUserDetailServiceTest {

    @Autowired
    JwtUserDetailService jwtUserDetailService;

    @Autowired
    MemberService memberService;

    @Test
    public void User이름으로UserDetails불러오기() {
        //given
        JwtRequest signupRequest = new JwtRequest();
        signupRequest.setUsername("testUser");
        signupRequest.setPassword("1234");
        memberService.signUp(signupRequest);

        //when
        UserDetails userDetails = jwtUserDetailService.loadUserByUsername("testUser");

        //then
        assertThat(userDetails.getUsername()).isEqualTo(signupRequest.getUsername());
    }
}