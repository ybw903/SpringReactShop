package com.springreactshop.demo.resource;

import com.springreactshop.demo.controller.MemberController;
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
    public MemberResource(MemberDto.InfoResponse memberDtoInfoResponse ) {
        this.username = memberDtoInfoResponse.getUsername();
        this.zipcode = memberDtoInfoResponse.getZipcode();
        this.street = memberDtoInfoResponse.getStreet();
        this.phone = memberDtoInfoResponse.getPhone();
        add(linkTo(MemberController.class).slash(this.username).withSelfRel());
        add(linkTo(MemberController.class).slash(this.username).withRel("update-user"));

    }
}
