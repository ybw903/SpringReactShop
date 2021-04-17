package com.springreactshop.demo.controller;

import com.springreactshop.demo.dto.ProductDto;
import com.springreactshop.demo.resource.ProductResource;
import com.springreactshop.demo.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/products", produces = MediaTypes.HAL_JSON_VALUE)
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResource> addProduct(@RequestBody ProductDto.Request productRequest,
                                                      Errors errors) {
        ProductDto.Response prouctResponse = productService.addProduct(productRequest);
        URI createdUri =linkTo(ProductController.class).slash(prouctResponse.getId()).toUri();
        ProductResource productResource = new ProductResource(prouctResponse);
        return ResponseEntity.created(createdUri).body(productResource);
    }

    @GetMapping
    public ResponseEntity<PagedModel<ProductResource>> productList(Pageable pageable,
                                                                   PagedResourcesAssembler<ProductDto.Response> assembler) {
        Page<ProductDto.Response> page =this.productService.productsPages(pageable);
        var pageResource = assembler.toModel(page, ProductResource::new);
        return ResponseEntity.ok(pageResource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResource> getProduct(@PathVariable Long id) {
        ProductDto.Response productResponse = this.productService.getProductResponseById(id);

        ProductResource productResource = new ProductResource(productResponse);
        return ResponseEntity.ok(productResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResource> updateProduct(@PathVariable Long id,
                                                         @RequestBody ProductDto.Request productDto) {
        ProductDto.Response productResponse = this.productService.update(id, productDto);
        ProductResource productResource = new ProductResource(productResponse);
        return ResponseEntity.ok(productResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("success");
        // https://stackoverflow.com/questions/25970523/restful-what-should-a-delete-response-body-contain/25970628
    }


}
