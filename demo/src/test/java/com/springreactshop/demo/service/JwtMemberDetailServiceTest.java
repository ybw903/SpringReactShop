package com.springreactshop.demo.service;

import com.springreactshop.demo.domain.Member;
import com.springreactshop.demo.domain.MemberRole;
import com.springreactshop.demo.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JwtMemberDetailServiceTest {

    @Autowired
    JwtMemberDetailService jwtMemberDetailService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void User이름으로UserDetails불러오기() {
        //given
        Member member = Member.builder()
                        .username("testUser")
                        .password("1234")
                        .memberRole(MemberRole.USER)
                        .build();
        memberRepository.save(member);

        //when
        UserDetails userDetails = jwtMemberDetailService.loadUserByUsername("testUser");

        //then
        assertThat(userDetails.getUsername()).isEqualTo(member.getUsername());
    }
}