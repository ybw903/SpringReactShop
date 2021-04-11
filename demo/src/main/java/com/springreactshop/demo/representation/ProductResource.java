package com.springreactshop.demo.representation;

import com.springreactshop.demo.controller.ProductController;
import com.springreactshop.demo.domain.Product;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class ProductResource extends EntityModel<Product> {

    public ProductResource(Product product, Link... links) {
        add(linkTo(ProductController.class).slash(product.getId()).withSelfRel());
    }
}
