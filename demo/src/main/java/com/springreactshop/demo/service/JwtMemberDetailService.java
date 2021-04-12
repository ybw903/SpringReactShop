package com.springreactshop.demo.service;

import com.springreactshop.demo.representation.JwtRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtMemberDetailService implements UserDetailsService {

    @Autowired
    private final MemberService memberService;

    public JwtMemberDetailService(MemberService memberService) {
        this.memberService = memberService;
    }

    // TODO: userDetail implement
    // TestID : "user_id", TestPW : "user_pw"
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberService.getUserByUsername(username);
    }

    public String addUser(JwtRequest signupRequest) {
        return memberService.signUpUser(signupRequest);
    }


}
