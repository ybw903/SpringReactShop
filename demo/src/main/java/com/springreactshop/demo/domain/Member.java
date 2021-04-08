package com.springreactshop.demo.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.springreactshop.demo.representation.MemberDto;
import com.springreactshop.demo.representation.UserDto;
import lombok.*;
import org.aspectj.weaver.ast.Or;

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

    @OneToMany(mappedBy = "member")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    public List<Order> orders = new ArrayList<>();

    @Builder
    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //==회원 정보 갱신==//
    public void updateMember(MemberDto memberDto) {
        Address address = new Address();
        if(this.address==null)this.address = address;
        this.address.setPhone(memberDto.getPhone());
        this.address.setStreet(memberDto.getStreet());
        this.address.setZipcode(memberDto.getZipcode());
    }
}
