package com.springreactshop.demo.controller;

import com.springreactshop.demo.domain.Member;
import com.springreactshop.demo.domain.Order;
import com.springreactshop.demo.dto.MemberDto;
import com.springreactshop.demo.resource.MemberResource;
import com.springreactshop.demo.resource.OrderResource;
import com.springreactshop.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/members", produces = MediaTypes.HAL_JSON_VALUE)
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{username}")
    public ResponseEntity<MemberResource> getUserInfo(@PathVariable String username) {
        Member member = memberService.getMemberProfileByUserName(username);
        return ResponseEntity.ok().body(new MemberResource(member));
    }

    @PutMapping("/{username}")
    public ResponseEntity<MemberResource> updateUserProfile (@PathVariable String username,
                      @RequestBody MemberDto.AddressUpdateRequest  memberUpdateAddressRequest) {
        Member updatedMember = memberService.updateMember(username, memberUpdateAddressRequest);
        return ResponseEntity.ok().body(new MemberResource(updatedMember));
    }

    @GetMapping("/{username}/orders")
    public ResponseEntity<CollectionModel<OrderResource>> getAllOrderByUserId(@PathVariable String username) {
        List<Order> orders = memberService.getOrders(username);
        CollectionModel<OrderResource> orderResources = CollectionModel.of(
                orders.stream()
                        .map(OrderResource::new)
                        .collect(Collectors.toList())
        );
        return ResponseEntity.ok(orderResources);
    }

}
