package com.springreactshop.demo.exception;

public class OrderNotFoundException extends RuntimeException{
    private static final String MESSAGE = "해당하는 상품을 찾을 수 없습니다.";

    public OrderNotFoundException() {
        super(MESSAGE);
    }
}
