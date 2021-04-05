package com.springreactshop.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter
public class Address {

    private String zipcode;
    private String street;
    private String phone;

    protected Address() {

    }

    public Address(String zipcode, String street, String phone) {
        this.zipcode = zipcode;
        this.street = street;
        this.phone = phone;
    }
}
