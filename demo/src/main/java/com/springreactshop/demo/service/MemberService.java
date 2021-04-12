package com.springreactshop.demo.service;

import com.springreactshop.demo.domain.Member;
import com.springreactshop.demo.repository.MemberRepository;
import com.springreactshop.demo.representation.JwtRequest;
import com.springreactshop.demo.representation.MemberDto;
import com.springreactshop.demo.representation.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public String signUp(JwtRequest signupRequest){
        Member member = Member.builder()
                .username(signupRequest.getUsername())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .build();
        return memberRepository.save(member).getUsername();
    }

    public Member getMemberProfileByUserName(String username)  {
        Member user = memberRepository.findByUsername(username);
        if(user == null) {
            return null;
        }
        return user;
    }

    public Member updateMember(Member member, MemberDto memberDto) {
        member.updateMember(memberDto);
        return memberRepository.save(member); //>???
    }

    public UserDto getUserByUsername(String username) {
        Member user = memberRepository.findByUsername(username);
        if(user == null) {
            return null;
        }

        UserDto userDto = createUserDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                "USER"
        );
        return userDto;
    }

    public UserDto createUserDto(Long id, String username, String password, String role){
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setUsername(username);
        userDto.setPassword(password);
        userDto.setRole(role);
        return userDto;
    }

}
