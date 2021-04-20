package com.springreactshop.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springreactshop.demo.dto.MemberDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private MemberRole roles;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    public List<Order> orders = new ArrayList<>();

    @Builder
    public Member(String username, String password, MemberRole memberRole) {
        this.username = username;
        this.password = password;
        this.roles = memberRole;
    }

    //==회원 정보 갱신==//
    public void updateMember(MemberDto.AddressUpdateRequest memberUpdateAddressRequest) {
        Address address = new Address();
        if(this.address==null)this.address = address;
        this.address.setPhone(memberUpdateAddressRequest.getPhone());
        this.address.setStreet(memberUpdateAddressRequest.getStreet());
        this.address.setZipcode(memberUpdateAddressRequest.getZipcode());
    }
}
