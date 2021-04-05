package com.springreactshop.demo.domain;

import com.springreactshop.demo.exception.NotEnoughStockException;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long Id;

    private String productName;

    private String productDescription;

    private int productPrice;

    private int productQuantity;

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

}
