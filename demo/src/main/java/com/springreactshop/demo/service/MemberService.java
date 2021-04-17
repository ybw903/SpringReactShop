package com.springreactshop.demo.service;

import com.springreactshop.demo.domain.Member;
import com.springreactshop.demo.domain.MemberRole;
import com.springreactshop.demo.dto.AuthDto;
import com.springreactshop.demo.dto.MemberDto;
import com.springreactshop.demo.repository.MemberRepository;
import com.springreactshop.demo.dto.MemberDetails;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public String signUpUser(AuthDto.Request signupRequest){
        Member member = Member.builder()
                .username(signupRequest.getUsername())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .memberRole(MemberRole.USER)
                .build();
        return memberRepository.save(member).getUsername();
    }

    public MemberDto.InfoResponse getMemberProfileByUserName(String username) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        Member member = optionalMember.orElseThrow(()->new UsernameNotFoundException(username));
        return MemberDto.InfoResponse.of(member);
    }

    public MemberDto.InfoResponse updateMember(String username, MemberDto.AddressUpdateRequest memberUpdateAddressRequest) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        Member member = optionalMember.orElseThrow(()-> new UsernameNotFoundException(username));
        member.updateMember(memberUpdateAddressRequest);
        return MemberDto.InfoResponse.of(member); //>???
    }

    public MemberDetails getUserByUsername(String username){
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        Member member = optionalMember.orElseThrow(()-> new UsernameNotFoundException(username));

        return createMemberDto(
                member.getId(),
                member.getUsername(),
                member.getPassword(),
                member.getRoles().name()
        );
    }

    public MemberDetails createMemberDto(Long id, String username, String password, String role){
        MemberDetails memberDetails = new MemberDetails();
        memberDetails.setId(id);
        memberDetails.setUsername(username);
        memberDetails.setPassword(password);
        memberDetails.setRole(role);
        return memberDetails;
    }

}
