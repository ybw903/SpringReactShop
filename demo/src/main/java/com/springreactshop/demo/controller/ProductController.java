package com.springreactshop.demo.controller;

import com.springreactshop.demo.domain.Product;
import com.springreactshop.demo.representation.ProductDto;
import com.springreactshop.demo.representation.ProductResource;
import com.springreactshop.demo.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/products", produces = MediaTypes.HAL_JSON_VALUE)
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResource> addProduct(@RequestBody ProductDto productDto, Errors errors) {

        Product product = productDto.toEntity();
        Long productId = productService.addProduct(product);
        WebMvcLinkBuilder selfLinkBuilder = linkTo(ProductController.class).slash(productId);
        URI createdUri =selfLinkBuilder.toUri();
        ProductResource productResource = new ProductResource(product);

        return ResponseEntity.created(createdUri).body(productResource);
    }

    @GetMapping
    public ResponseEntity<PagedModel<ProductResource>> productList(Pageable pageable, PagedResourcesAssembler<Product> assembler) {
        Page<Product> page =this.productService.productsPages(pageable);
        var pageResource = assembler.toModel(page, p-> new ProductResource(p));
        return ResponseEntity.ok(pageResource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResource> getProduct(@PathVariable Long id) {
        Optional<Product>optionalProduct = this.productService.getProduct(id);
        if(optionalProduct.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Product product = optionalProduct.get();
        ProductResource productResource = new ProductResource(product);
        return ResponseEntity.ok(productResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResource> updateProduct(@PathVariable Long id,
                                                         @RequestBody ProductDto productDto) {

        Optional<Product> optionalProduct = this.productService.getProduct(id);
        if(optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product existingProduct = optionalProduct.get();
        existingProduct = productDto.toEntity();
        Long savedProductId = this.productService.addProduct(existingProduct);

        ProductResource productResource = new ProductResource(existingProduct);
        return ResponseEntity.ok(productResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id) {

        Optional<Product> optionalProduct = this.productService.getProduct(id);
        if(optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        productService.deleteProduct(id);
        //deleteProduct(id);

        return ResponseEntity.ok().build();
    }


}
