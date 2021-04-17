package com.springreactshop.demo.dto;

import com.springreactshop.demo.domain.Member;
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

        public InfoResponse(Member member) {
            if(member.getAddress()==null) {
                this.username=member.getUsername();
                this.zipcode="";
                this.street="";
                this.phone="";
            } else {
                this.username=member.getUsername();
                this.zipcode=member.getAddress().getZipcode();
                this.street=member.getAddress().getStreet();
                this.phone=member.getAddress().getZipcode();
            }
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
