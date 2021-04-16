package com.springreactshop.demo.controller;

import com.springreactshop.demo.domain.Member;
import com.springreactshop.demo.dto.MemberUpdateAddressRequest;
import com.springreactshop.demo.dto.MemberProfileResponse;
import com.springreactshop.demo.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/members", produces = MediaTypes.HAL_JSON_VALUE)
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserProfile(@PathVariable String username) {
        Member member = memberService.getMemberProfileByUserName(username);
        if(member == null) return ResponseEntity.notFound().build();

        MemberProfileResponse memberProfileResponse = new MemberProfileResponse(member);

        WebMvcLinkBuilder selfLinkBuilder = linkTo(MemberController.class).slash(memberProfileResponse.getUsername());
        EntityModel<MemberProfileResponse> memberResource = EntityModel.of(memberProfileResponse);
        memberResource.add(selfLinkBuilder.withSelfRel());

        return ResponseEntity.ok().body(memberResource);
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUserProfile(@PathVariable String username, @RequestBody MemberUpdateAddressRequest memberUpdateAddressRequest) {
        Member member = memberService.getMemberProfileByUserName(username);
        if(member == null) return ResponseEntity.notFound().build();

        member= memberService.updateMember(member, memberUpdateAddressRequest);

        MemberProfileResponse memberProfileResponse = new MemberProfileResponse(member);
        WebMvcLinkBuilder selfLinkBuilder = linkTo(MemberController.class).slash(memberProfileResponse.getUsername());

        EntityModel<MemberProfileResponse> memberResource = EntityModel.of(memberProfileResponse);
        memberResource.add(selfLinkBuilder.withSelfRel());

        return ResponseEntity.ok().body(memberResource);
    }


}
