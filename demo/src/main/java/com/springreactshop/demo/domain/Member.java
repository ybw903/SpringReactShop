package com.springreactshop.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@Builder @Setter
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String userName;
}
