package com.springreactshop.demo.representation;

import com.springreactshop.demo.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberProfileResponse implements Serializable {
    private String username;
    private String zipcode;
    private String street;
    private String phone;

    public MemberProfileResponse(Member member) {
        if(member.getAddress()==null) {
            this.username=member.getUsername();
            this.zipcode="";
            this.street="";
            this.zipcode="";
        } else {
            this.username=member.getUsername();
            this.zipcode=member.getAddress().getZipcode();
            this.street=member.getAddress().getStreet();
            this.zipcode=member.getAddress().getZipcode();
        }
    }
}
