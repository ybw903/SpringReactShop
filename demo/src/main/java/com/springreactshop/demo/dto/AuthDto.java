package com.springreactshop.demo.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

public class AuthDto {
    @Data
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class Request{
        @NotEmpty
        private String username;
        @NotEmpty
        private String password;
    }

    @Getter
    @AllArgsConstructor
    public static class Response{
        private final String jwttoken;
    }
}
