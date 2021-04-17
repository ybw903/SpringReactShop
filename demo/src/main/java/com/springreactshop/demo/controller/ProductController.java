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
    public ResponseEntity<ProductResource> addProduct(@RequestBody ProductDto.Request productRequest,
                                                      Errors errors) {

        ProductDto.Response prouctResponse = productService.addProduct(productRequest);
        WebMvcLinkBuilder selfLinkBuilder = linkTo(ProductController.class).slash(prouctResponse.getId());
        URI createdUri =selfLinkBuilder.toUri();
        ProductResource productResource = new ProductResource(productRequest.toEntity());

        return ResponseEntity.created(createdUri).body(productResource);
    }

    @GetMapping
    public ResponseEntity<PagedModel<ProductResource>> productList(Pageable pageable, PagedResourcesAssembler<Product> assembler) {
        Page<Product> page =this.productService.productsPages(pageable);
        var pageResource = assembler.toModel(page, ProductResource::new);
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
                                                         @RequestBody ProductDto.Request productDto) {

        Optional<Product> optionalProduct = this.productService.getProduct(id);
        if(optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product existingProduct = optionalProduct.get();
        existingProduct = productDto.toEntity();

        ProductResource productResource = new ProductResource(existingProduct);
        return ResponseEntity.ok(productResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {

        Optional<Product> optionalProduct = this.productService.getProduct(id);
        if(optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        productService.deleteProduct(id);

        return ResponseEntity.ok("success");
        // https://stackoverflow.com/questions/25970523/restful-what-should-a-delete-response-body-contain/25970628
    }


}
