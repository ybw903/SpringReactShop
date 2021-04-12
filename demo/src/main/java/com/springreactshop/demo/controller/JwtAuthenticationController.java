package com.springreactshop.demo.controller;

import com.springreactshop.demo.configuration.JwtTokenUtil;
import com.springreactshop.demo.representation.JwtRequest;
import com.springreactshop.demo.representation.JwtResponse;
import com.springreactshop.demo.service.JwtUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@CrossOrigin
@Slf4j
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailService userDetailService;

    @PostMapping(value = "/api/authenticate/login")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody JwtRequest authenticateRequest) throws Exception {
        authenticate(authenticateRequest.getUsername(), authenticateRequest.getPassword());

        final UserDetails userDetails = userDetailService
                .loadUserByUsername(authenticateRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }


    @PostMapping(value = "/api/authenticate/signup")
    public ResponseEntity<?> signUp(@RequestBody JwtRequest signupRequest) {

        String userName = userDetailService.addUser(signupRequest);
        WebMvcLinkBuilder selfLinkBuilder = linkTo(MemberController.class).slash(userName);
        URI createdUri = selfLinkBuilder.toUri();
        return ResponseEntity.created(createdUri).body("ok");
    }



    private void authenticate(String username, String password) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}