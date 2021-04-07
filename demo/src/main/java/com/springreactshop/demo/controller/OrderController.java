package com.springreactshop.demo.controller;

import com.springreactshop.demo.domain.Member;
import com.springreactshop.demo.domain.Order;
import com.springreactshop.demo.representation.OrderRequest;
import com.springreactshop.demo.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/orders", produces = MediaTypes.HAL_JSON_VALUE)
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> order(@RequestBody OrderRequest orderRequest) {
        Order order = orderService.order(orderRequest);

        WebMvcLinkBuilder selfLinkBuilder = linkTo(ProductController.class).slash(order.getId());
        URI createdUri =selfLinkBuilder.toUri();

        EntityModel<Order> orderResource = EntityModel.of(order);
        orderResource.add(selfLinkBuilder.withSelfRel());

        return ResponseEntity.created(createdUri).body(orderResource);
    }
}
