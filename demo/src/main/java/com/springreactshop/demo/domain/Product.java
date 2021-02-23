package com.springreactshop.demo.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue
    private Long Id;

    private String productName;
    private Double productPrice;

}
