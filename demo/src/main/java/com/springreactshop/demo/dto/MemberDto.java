package com.springreactshop.demo.dto;

import com.springreactshop.demo.domain.Member;
import com.springreactshop.demo.domain.Product;
import lombok.*;

import javax.validation.constraints.NotEmpty;

public class MemberDto {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InfoResponse{
        private String username;
        private String zipcode;
        private String street;
        private String phone;

        public static InfoResponse of(Member member) {
            return InfoResponse
                    .builder()
                    .username(member.getUsername())
                    .zipcode(member.getAddress()==null?"":member.getAddress().getZipcode())
                    .street(member.getAddress()==null?"":member.getAddress().getStreet())
                    .phone(member.getAddress()==null?"":member.getAddress().getPhone())
                    .build();
        }
    }

    @Data @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AddressUpdateRequest{
        private String zipcode;
        private String street;
        private String phone;
    }
}
