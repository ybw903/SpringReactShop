package com.springreactshop.demo.resource;

import com.springreactshop.demo.controller.MemberController;
import com.springreactshop.demo.domain.Member;
import com.springreactshop.demo.dto.MemberDto;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Getter
public class MemberResource extends RepresentationModel<MemberResource> {
    private final String username;
    private final String zipcode;
    private final String street;
    private final String phone;
    public MemberResource(Member member ) {
        this.username = member.getUsername();
        this.zipcode = member.getAddress()==null?"":member.getAddress().getZipcode();
        this.street = member.getAddress()==null?"":member.getAddress().getStreet();
        this.phone = member.getAddress()==null?"":member.getAddress().getPhone();
        add(linkTo(MemberController.class).slash(this.username).withSelfRel());
        add(linkTo(MemberController.class).slash(this.username).withRel("update-user"));

    }
}
