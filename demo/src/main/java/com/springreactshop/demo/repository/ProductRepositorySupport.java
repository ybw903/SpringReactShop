package com.springreactshop.demo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.springreactshop.demo.domain.Product;
import static com.springreactshop.demo.domain.QProduct.product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public List<Product> findByName(String name) {
        return queryFactory.selectFrom(product)
                .where(product.productName.eq(name))
                .fetch();
    }
}
