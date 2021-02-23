package com.springreactshop.demo.controller;

import com.springreactshop.demo.domain.Member;
import com.springreactshop.demo.repository.MemberRepository;
import com.springreactshop.demo.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class MemberController {

    private final MemberService memberService;


}
