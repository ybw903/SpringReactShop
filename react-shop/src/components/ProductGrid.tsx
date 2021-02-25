import React from 'react';
import {
    Container,
    Col,
    Row
} from 'react-bootstrap';
import { Product } from '../types/products';
import ProductCard from './ProductCard';

type ProductGridProps = {
    productList: Product[],
    columnCount?: number,
}

const ProductGrid =({productList, columnCount =3}: ProductGridProps) => {
    return(
        <Container className="product-container">
            <Row>
                {productList.map((product, j) =>{
                    return(
                        <Col key={j} md={3}>
                            <ProductCard product={product}></ProductCard>
                        </Col>
                    );
                })
                }
            </Row>

        </Container>
    )
}

export default ProductGrid;