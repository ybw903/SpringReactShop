package com.springreactshop.demo.controller;

import com.springreactshop.demo.dto.MemberDto;
import com.springreactshop.demo.resource.MemberResource;
import com.springreactshop.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/members", produces = MediaTypes.HAL_JSON_VALUE)
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{username}")
    public ResponseEntity<MemberResource> getUserInfo(@PathVariable String username) {
        MemberResource memberResource = memberService.getMemberProfileByUserName(username);
        return ResponseEntity.ok().body(memberResource);
    }

    @PutMapping("/{username}")
    public ResponseEntity<MemberResource> updateUserProfile (@PathVariable String username,
                      @RequestBody MemberDto.AddressUpdateRequest  memberUpdateAddressRequest) {
        MemberResource memberResource = memberService.updateMember(username, memberUpdateAddressRequest);
        return ResponseEntity.ok().body(memberResource);
    }

    @GetMapping("/{username}/orders")
    public ResponseEntity<MemberDto.Orders> getAllOrderByUserId(@PathVariable String username) {
        MemberDto.Orders meberOrders = memberService.getOrders(username);
        return ResponseEntity.ok(meberOrders);
    }

}
