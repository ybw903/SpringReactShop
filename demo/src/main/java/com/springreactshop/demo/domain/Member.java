package com.springreactshop.demo.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Setter @Getter
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String userName;

}
