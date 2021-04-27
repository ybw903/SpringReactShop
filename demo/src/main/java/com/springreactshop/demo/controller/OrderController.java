package com.springreactshop.demo.controller;

import com.springreactshop.demo.domain.Member;
import com.springreactshop.demo.domain.Order;
import com.springreactshop.demo.dto.OrderDto;
import com.springreactshop.demo.resource.OrderResource;
import com.springreactshop.demo.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/orders", produces = MediaTypes.HAL_JSON_VALUE)
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResource> order(@Valid @RequestBody OrderDto.Request orderRequest) {
        Order order = orderService.order(orderRequest);
        URI createdUri =linkTo(OrderController.class).slash(order.getId()).toUri();
        return ResponseEntity.created(createdUri).body(new OrderResource(order));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResource> getOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrder(orderId);
        return  ResponseEntity.ok(new OrderResource(order));
    }

    @GetMapping
    public ResponseEntity<PagedModel<OrderResource>> orderList(Pageable page, PagedResourcesAssembler<Order> assembler) {
        Page<Order> orderPage = orderService.getOrderPage(page);
        PagedModel<OrderResource> orderResourcePagedModel = assembler.toModel(orderPage, OrderResource::new);
        return ResponseEntity.ok(orderResourcePagedModel);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<OrderResource> cancelOrder(@PathVariable Long orderId) {
        Order order = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(new OrderResource(order));
    }
}
