package com.springreactshop.demo.controller;

import com.springreactshop.demo.representation.OrderRequest;
import com.springreactshop.demo.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/orders", produces = MediaTypes.HAL_JSON_VALUE)
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> order(@RequestBody OrderRequest orderRequest) {
        orderService.order(orderRequest);
        return ResponseEntity.ok(null);
    }
}
