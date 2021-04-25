package com.springreactshop.demo.domain;

import com.springreactshop.demo.dto.ProductDto;
import com.springreactshop.demo.exception.NotEnoughStockException;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String productName;

    private String productDescription;

    private int productPrice;

    private int productQuantity;

    //TODO : 확장을 고려해서 고칠 것
    @Enumerated(EnumType.STRING)
    private Category category;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;


    //==비즈니스 로직==//
    public void addStock(int quantity) {
        this.productQuantity +=quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.productQuantity - quantity;
        if(restStock < 0 ) {
            throw new NotEnoughStockException("need more stock");
        }
        this.productQuantity = restStock;
    }

    public Product update(ProductDto.Request productRequest) {
        this.productName = productRequest.getProductName();
        this.productDescription = productRequest.getProductDescription();
        this.productPrice = productRequest.getProductPrice();
        this.productQuantity = productRequest.getProductQuantity();
        return this;
    }

}
