package com.springreactshop.demo.handler.exception;

import com.springreactshop.demo.exception.OrderNotFoundException;
import com.springreactshop.demo.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {OrderNotFoundException.class, ProductNotFoundException.class})
    public String handleBaseException(Exception e){
        return e.getMessage();
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler

}
