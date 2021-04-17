package com.springreactshop.demo.controller;

import com.springreactshop.demo.domain.Member;
import com.springreactshop.demo.dto.MemberDto;
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
    public ResponseEntity<EntityModel<MemberDto.InfoResponse>> getUserInfo(@PathVariable String username) {
        Member member = memberService.getMemberProfileByUserName(username);
        if(member == null) return ResponseEntity.notFound().build();

        MemberDto.InfoResponse memberInfoResponse = new MemberDto.InfoResponse(member);

        WebMvcLinkBuilder selfLinkBuilder = linkTo(MemberController.class).slash(memberInfoResponse.getUsername());
        EntityModel<MemberDto.InfoResponse> memberResource = EntityModel.of(memberInfoResponse);
        memberResource.add(selfLinkBuilder.withSelfRel());

        return ResponseEntity.ok().body(memberResource);
    }

    @PutMapping("/{username}")
    public ResponseEntity<EntityModel<MemberDto.InfoResponse>> updateUserProfile (@PathVariable String username,
                      @RequestBody MemberDto.AddressUpdateRequest  memberUpdateAddressRequest) {
        Member member = memberService.getMemberProfileByUserName(username);
        if(member == null) return ResponseEntity.notFound().build();

        member= memberService.updateMember(member, memberUpdateAddressRequest);

        MemberDto.InfoResponse memberInfoResponse = new MemberDto.InfoResponse(member);
        WebMvcLinkBuilder selfLinkBuilder = linkTo(MemberController.class).slash(memberInfoResponse.getUsername());

        EntityModel<MemberDto.InfoResponse> memberResource = EntityModel.of(memberInfoResponse);
        memberResource.add(selfLinkBuilder.withSelfRel());

        return ResponseEntity.ok().body(memberResource);
    }


}
