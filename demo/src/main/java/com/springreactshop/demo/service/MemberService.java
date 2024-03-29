package com.springreactshop.demo.service;

import com.springreactshop.demo.domain.Member;
import com.springreactshop.demo.domain.MemberRole;
import com.springreactshop.demo.domain.Order;
import com.springreactshop.demo.dto.AuthDto;
import com.springreactshop.demo.dto.MemberDto;
import com.springreactshop.demo.dto.OrderDto;
import com.springreactshop.demo.repository.MemberRepository;
import com.springreactshop.demo.dto.MemberDetails;
import com.springreactshop.demo.resource.MemberResource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Member getMemberProfileByUserName(String username) {
        Member member = findMemberByUsername(username);
        return member;
    }
    public Page<Member> getMembers(Pageable pageable) {
        Page<Member> members = memberRepository.findAll(pageable);
        return members;
    }

    public Member updateMember(String username, MemberDto.AddressUpdateRequest memberUpdateAddressRequest) {
        Member member = findMemberByUsername(username);
        member.updateMember(memberUpdateAddressRequest);
        return member;
    }

    public List<Order> getOrdersByUsername(String username) {
        Member member = findMemberByUsername(username);
        return member.getOrders();
    }

    public Member findMemberByUsername(String username) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        return optionalMember.orElseThrow(()-> new UsernameNotFoundException(username));
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
