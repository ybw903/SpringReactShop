package com.springreactshop.demo.controller;

import com.springreactshop.demo.domain.Member;
import com.springreactshop.demo.dto.MemberDto;
import com.springreactshop.demo.resource.MemberResource;
import com.springreactshop.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/members", produces = MediaTypes.HAL_JSON_VALUE)
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{username}")
    public ResponseEntity<MemberResource> getUserInfo(@PathVariable String username) {
        Member member = memberService.getMemberProfileByUserName(username);
        if(member == null) return ResponseEntity.notFound().build();
        MemberDto.InfoResponse memberInfoResponse = new MemberDto.InfoResponse(member);

        MemberResource memberResource = new MemberResource(memberInfoResponse);
        return ResponseEntity.ok().body(memberResource);
    }

    @PutMapping("/{username}")
    public ResponseEntity<MemberResource> updateUserProfile (@PathVariable String username,
                      @RequestBody MemberDto.AddressUpdateRequest  memberUpdateAddressRequest) {

        Member updatedMember = memberService.updateMember(username, memberUpdateAddressRequest);
        MemberDto.InfoResponse memberInfoResponse = new MemberDto.InfoResponse(updatedMember);
        MemberResource memberResource = new MemberResource(memberInfoResponse);

        return ResponseEntity.ok().body(memberResource);
    }


}
