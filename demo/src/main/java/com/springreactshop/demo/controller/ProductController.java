package com.springreactshop.demo.controller;

import com.springreactshop.demo.domain.Product;
import com.springreactshop.demo.dto.ProductDto;
import com.springreactshop.demo.resource.ProductResource;
import com.springreactshop.demo.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/products", produces = MediaTypes.HAL_JSON_VALUE)
public class ProductController {

    private final ProductService productService;

    //TODO: validate Form
    @PostMapping
    public ResponseEntity<ProductResource> addProduct(@RequestBody ProductDto.Request productRequest) {
        Product product = productService.addProduct(productRequest);
        URI createdUri =linkTo(ProductController.class).slash(product.getId()).toUri();
        return ResponseEntity.created(createdUri).body(new ProductResource(product));
    }

    @GetMapping
    public ResponseEntity<PagedModel<ProductResource>> productList(Pageable pageable, PagedResourcesAssembler<Product> assembler) {
        Page<Product> productPage = productService.getProductPage(pageable);
        PagedModel<ProductResource> productResourcePagedModel = assembler.toModel(productPage, ProductResource::new);
        return ResponseEntity.ok(productResourcePagedModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResource> getProduct(@PathVariable Long id) {
        Product product = productService.getProductResponseById(id);
        return ResponseEntity.ok(new ProductResource(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResource> updateProduct(@PathVariable Long id,
                                                         @RequestBody ProductDto.Request productDto) {
        Product product = productService.update(id, productDto);
        return ResponseEntity.ok(new ProductResource(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("success");
        // https://stackoverflow.com/questions/25970523/restful-what-should-a-delete-response-body-contain/25970628
    }


}
