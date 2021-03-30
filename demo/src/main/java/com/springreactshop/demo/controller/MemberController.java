package com.springreactshop.demo.controller;

import com.springreactshop.demo.domain.Member;
import com.springreactshop.demo.domain.Product;
import com.springreactshop.demo.repository.MemberRepository;
import com.springreactshop.demo.representation.JwtRequest;
import com.springreactshop.demo.representation.ProductDto;
import com.springreactshop.demo.representation.ProductResource;
import com.springreactshop.demo.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class MemberController {

    private final MemberService memberService;

    @PostMapping(value = "/signup")
    public ResponseEntity<?> addUser(@RequestBody JwtRequest signupRequest) {

        Long userId = memberService.signUp(signupRequest);
        WebMvcLinkBuilder selfLinkBuilder = linkTo(MemberController.class).slash(userId);
        URI createdUri = selfLinkBuilder.toUri();
        return ResponseEntity.created(createdUri).body("ok");
    }


}
