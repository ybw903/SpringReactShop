import React from 'react';
import { ListGroup } from 'react-bootstrap';
import { connect, ConnectedProps } from 'react-redux';
import { removeFromCart } from '../actions/cart';
import { RootState } from '../store';

const mapState = ({cartState}: RootState) =>({
    products: cartState.products
});

const mapDispatch = {
    onRemoveFromCart: (location: number) => removeFromCart(location),
}

const connector = connect(
    mapState,
    mapDispatch,
);

type PropsFromRedux = ConnectedProps<typeof connector>

type Props = PropsFromRedux& {

}

const CartItems = ({products, onRemoveFromCart}: Props) => {
    return(
        <ListGroup>
            { products.map((product, i) =>(
                <ListGroup.Item key={i}>
                    {product.productName}
                    <span>${product.productPrice}<span>{product.quantity}</span></span>
                    <span></span>
                </ListGroup.Item>
            ))}
        </ListGroup>
    );
};

export default connector(CartItems);