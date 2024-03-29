package com.springreactshop.demo.controller;

import com.springreactshop.demo.dto.AuthDto;
import com.springreactshop.demo.security.JwtTokenUtil;
import com.springreactshop.demo.service.JwtMemberDetailService;
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

import javax.validation.Valid;
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
    private JwtMemberDetailService userDetailService;

    @PostMapping(value = "/api/authenticate/login")
    public ResponseEntity<AuthDto.Response> createAuthenticationToken(
           @Valid @RequestBody AuthDto.Request authenticateRequest) throws Exception {
        authenticate(authenticateRequest.getUsername(), authenticateRequest.getPassword());

        final UserDetails userDetails = userDetailService
                .loadUserByUsername(authenticateRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthDto.Response(token));
    }


    @PostMapping(value = "/api/authenticate/signup")
    public ResponseEntity<String> signUp(@RequestBody AuthDto.Request signupRequest) {

        String userName = userDetailService.addUser(signupRequest);
        URI createdUri = linkTo(MemberController.class).slash(userName).toUri();
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
