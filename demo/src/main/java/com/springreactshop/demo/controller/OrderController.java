package com.springreactshop.demo.controller;

import com.springreactshop.demo.domain.Order;
import com.springreactshop.demo.representation.OrderRequest;
import com.springreactshop.demo.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/orders", produces = MediaTypes.HAL_JSON_VALUE)
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> order(@RequestBody OrderRequest orderRequest) {
        Order order = orderService.order(orderRequest);

        WebMvcLinkBuilder selfLinkBuilder = linkTo(OrderController.class).slash(order.getId());
        URI createdUri =selfLinkBuilder.toUri();

        EntityModel<Order> orderResource = EntityModel.of(order);
        orderResource.add(selfLinkBuilder.withSelfRel());
        orderResource.add(selfLinkBuilder.withRel("cancel-order"));

        return ResponseEntity.created(createdUri).body(orderResource);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {
        Optional<Order> optionalOrder = orderService.getOrder(orderId);
        if(optionalOrder.isEmpty())
            return ResponseEntity.notFound().build();

        Order order = optionalOrder.get();
        order = orderService.cancelOrder(order.getId());
        WebMvcLinkBuilder selfLinkBuilder = linkTo(OrderController.class).slash(order.getId());
        EntityModel<Order> orderResource = EntityModel.of(order);
        orderResource.add(selfLinkBuilder.withSelfRel());

        return ResponseEntity.ok(orderResource);
    }

}
