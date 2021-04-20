package com.springreactshop.demo.service;

import com.springreactshop.demo.domain.*;
import com.springreactshop.demo.dto.MemberDto;
import com.springreactshop.demo.dto.OrderDto;
import com.springreactshop.demo.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    MemberService memberService;

    @Test
    void 주문한_상품이_없을때_회원주문목록조회() {
        //given
        Member member = Member.builder()
                            .username("testUser")
                            .build();

        given(memberRepository.findByUsername("testUser")).willReturn(Optional.of(member));

        //when
        MemberDto.Orders memberOrderResponse = memberService.getOrders("testUser");

        //then
        assertThat(memberOrderResponse.getOrders().size()).isZero();
    }



}
