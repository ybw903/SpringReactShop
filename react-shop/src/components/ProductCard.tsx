import React from 'react';
import {
    Card,
    Button
} from 'react-bootstrap';
import { connect, ConnectedProps } from 'react-redux';
import { addToCart } from '../actions/cart';
import { Product } from '../types/products';

const mapDispatch = {
    onAddToCart : (product : Product) => addToCart(product),
}

const connector = connect(
    null,
    mapDispatch
);

type PropsFromRedux = ConnectedProps<typeof connector>

type Props = PropsFromRedux&{
    product: Product
}

const ProductCard = ({onAddToCart, product}: Props) => {
    return(
        <Card>
            <Card.Body>
                <Card.Title>{product.productName}</Card.Title>
                <Card.Text>{product.productDescription}</Card.Text>
                <span>{product.productPrice}</span>
                <Button className="cart-button" variant="outline-primary" onClick={()=>onAddToCart(product)}>카트에 담기</Button>
            </Card.Body>
        </Card>
    )
}

export default connector(ProductCard);