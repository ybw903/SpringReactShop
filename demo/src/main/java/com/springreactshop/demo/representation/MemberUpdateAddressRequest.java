package com.springreactshop.demo.representation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springreactshop.demo.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;

@Data @NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberUpdateAddressRequest {

    private String zipcode;
    private String street;
    private String phone;
}
