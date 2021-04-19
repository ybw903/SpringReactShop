package com.springreactshop.demo.controller;

import com.springreactshop.demo.domain.Order;
import com.springreactshop.demo.dto.OrderDto;
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
    PagedResourcesAssembler<OrderDto.Response> assembler;

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResource> order(@RequestBody OrderDto.Request orderRequest) {
        log.info(orderRequest.toString());
        OrderDto.Response orderResponse = orderService.order(orderRequest);
        URI createdUri =linkTo(OrderController.class).slash(orderResponse.getId()).toUri();
        OrderResource orderResource = new OrderResource(orderResponse);

        return ResponseEntity.created(createdUri).body(orderResource);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResource> getOrder(@PathVariable Long orderId) {
        OrderDto.Response orderResponse = orderService.getOrder(orderId);
        OrderResource orderResource = new OrderResource(orderResponse);
        return  ResponseEntity.ok(orderResource);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<OrderResource> cancelOrder(@PathVariable Long orderId) {
        OrderDto.Response orderResponse = orderService.cancelOrder(orderId);
        OrderResource orderResource = new OrderResource(orderResponse);
        return ResponseEntity.ok(orderResource);
    }

    @GetMapping
    public ResponseEntity<PagedModel<OrderResource>> orderList(Pageable page) {
        Page<OrderDto.Response> orders =this.orderService.orderPages(page);
        PagedModel<OrderResource> orderResource = assembler.toModel(orders, OrderResource::new);
        return ResponseEntity.ok(orderResource);
    }
}
