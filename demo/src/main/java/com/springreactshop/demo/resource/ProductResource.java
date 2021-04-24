package com.springreactshop.demo.resource;

import com.springreactshop.demo.controller.ProductController;
import com.springreactshop.demo.domain.Category;
import com.springreactshop.demo.domain.Product;
import com.springreactshop.demo.dto.ProductDto;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Getter
public class ProductResource extends RepresentationModel<ProductResource> {

    private final Long Id;

    private final String productName;

    private final String productDescription;

    private final int productPrice;

    private final int productQuantity;

    private final Category category;

    public ProductResource(Product product) {
        this.Id = product.getId();
        this.productName = product.getProductName();
        this.productDescription = product.getProductDescription();
        this.productPrice = product.getProductPrice();
        this.productQuantity = product.getProductQuantity();
        this.category = product.getCategory();
        add(linkTo(ProductController.class).slash(product.getId()).withSelfRel());
        add(linkTo(ProductController.class).withRel("productsList"));
        add(linkTo(ProductController.class).slash(product.getId()).withRel("update-product"));
    }
}
