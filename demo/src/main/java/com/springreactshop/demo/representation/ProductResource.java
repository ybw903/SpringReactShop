package com.springreactshop.demo.representation;

import com.springreactshop.demo.controller.ProductController;
import com.springreactshop.demo.domain.Product;
import lombok.Getter;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Getter
public class ProductResource extends RepresentationModel<ProductResource> {

    private final Long Id;

    private final String productName;

    private final String productDescription;

    private final int productPrice;

    private final int productQuantity;

    public ProductResource(Product product) {
        this.Id = product.getId();
        this.productName = product.getProductName();
        this.productDescription = product.getProductDescription();
        this.productPrice = product.getProductPrice();
        this.productQuantity = product.getProductQuantity();
        add(linkTo(ProductController.class).slash(product.getId()).withSelfRel());
    }
}
