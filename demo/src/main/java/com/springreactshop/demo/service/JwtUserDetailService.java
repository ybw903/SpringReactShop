package com.springreactshop.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailService implements UserDetailsService {

    @Autowired
    private MemberService memberService;

    public JwtUserDetailService(MemberService memberService) {
        this.memberService = memberService;
    }

    // TODO: userDetail implement
    // TestID : "user_id", TestPW : "user_pw"
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberService.getUserByUsername(username);
//        if ("user_id".equals(username)) {
//            return new User("user_id", "$2a$10$m/enYHaLsCwH2dKMUAtQp.ksGOA6lq7Fd2pnMb4L.yT4GyeAPRPyS",
//                    new ArrayList<>());
//        } else {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
    }


}
