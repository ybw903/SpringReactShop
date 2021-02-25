import React from 'react';
import {
    Card,
    Button
} from 'react-bootstrap';
import { Product } from '../types/products';

type Props = {
    product: Product
}

const ProductCard = ({product}: Props) => {
    return(
        <Card>
            <Card.Body>
                <Card.Title>{product.productName}</Card.Title>
                <Card.Text>{product.productDescription}</Card.Text>
                <span>{product.productPrice}</span>
                <Button className="cart-button" variant="outline-primary">카트에 담기</Button>
            </Card.Body>
        </Card>
    )
}

export default ProductCard;