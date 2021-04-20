package com.springreactshop.demo.dto;

import com.springreactshop.demo.domain.Member;
import com.springreactshop.demo.domain.Product;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

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
        @NotEmpty
        private String zipcode;
        @NotEmpty
        private String street;
        @NotEmpty
        private String phone;
    }

    @Data @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Orders{
        private List<OrderDto.Response> orders;
    }
}
