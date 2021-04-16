package com.springreactshop.demo.controller;

import com.springreactshop.demo.domain.Order;
import com.springreactshop.demo.dto.OrderRequest;
import com.springreactshop.demo.resource.OrderResource;
import com.springreactshop.demo.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/orders", produces = MediaTypes.HAL_JSON_VALUE)
@Slf4j
public class OrderController {

    @Autowired
    PagedResourcesAssembler<Order> assembler;

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> order(@RequestBody OrderRequest orderRequest) {
        log.info(orderRequest.toString());

        Order order = null;
        try {
            order = orderService.order(orderRequest);
        } catch (RuntimeException re) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        WebMvcLinkBuilder selfLinkBuilder = linkTo(OrderController.class).slash(order.getId());
        URI createdUri =selfLinkBuilder.toUri();

        EntityModel<Order> orderResource = EntityModel.of(order);
        orderResource.add(selfLinkBuilder.withSelfRel());
        orderResource.add(selfLinkBuilder.withRel("cancel-order"));

        return ResponseEntity.created(createdUri).body(orderResource);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable Long orderId) {
        Optional<Order> optionalOrder = orderService.getOrder(orderId);
        if(optionalOrder.isEmpty())
            return ResponseEntity.notFound().build();

        Order order = optionalOrder.get();
        WebMvcLinkBuilder selfLinkBuilder = linkTo(OrderController.class).slash(order.getId());
        EntityModel<Order> orderResource = EntityModel.of(order);
        orderResource.add(selfLinkBuilder.withSelfRel());
        orderResource.add(selfLinkBuilder.withRel("cancel-order"));

        return  ResponseEntity.ok(orderResource);
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

    @GetMapping
    public ResponseEntity<PagedModel<OrderResource>> orderList(Pageable page) {
        Page<Order> orders =this.orderService.orderPages(page);
        PagedModel<OrderResource> orderResource = assembler.toModel(orders, OrderResource::new);
        return ResponseEntity.ok(orderResource);
    }
}
