import React from 'react';
import {
    Container,
    Col,
    Row
} from 'react-bootstrap';
import ProductCard from './ProductCard';

const ProductGrid =() => {
    return(
        <Container className="product-container">
            <Row>
                <Col md={3}>
                    <ProductCard></ProductCard>
                </Col>
                <Col md={3}>
                    <ProductCard></ProductCard>
                </Col>
            </Row>

        </Container>
    )
}

export default ProductGrid;