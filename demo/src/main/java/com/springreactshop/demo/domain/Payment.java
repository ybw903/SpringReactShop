package com.springreactshop.demo.domain;

public enum Payment {
    CREDIT("CREDIT"),
    DEBIT("DEBIT"),
    PAYPAL("PAYPAL");

    String value;
    Payment(String value) {this.value = value;}
    public String value() {return value;}
}
