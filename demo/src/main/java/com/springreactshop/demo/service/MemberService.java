package com.springreactshop.demo.service;

import com.springreactshop.demo.domain.Member;
import com.springreactshop.demo.repository.MemberRepository;
import com.springreactshop.demo.representation.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public Long signUp(Member member){
        return memberRepository.save(member).getId();
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
