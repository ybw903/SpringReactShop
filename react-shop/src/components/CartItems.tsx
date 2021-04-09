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
    isEdit: boolean
}

const CartItems = ({isEdit, products, onRemoveFromCart}: Props) => {
    const cartTotal = products.reduce((total,prod) =>total+prod.productPrice*prod.orderQuantity,0);
    return(
        <ListGroup>
            { products.map((product, i) =>(
                <ListGroup.Item key={i}>
                    {product.productName}
                    
                    <span className="cart-price">${product.productPrice} <span className="text-muted">{product.orderQuantity}개</span>
                    {isEdit? <span className ="fa fa-times text-danger remove-item" onClick={()=> onRemoveFromCart(i)}></span>:<></>}
                    </span>
                    
                </ListGroup.Item>
            ))}
            <ListGroup.Item>
                <strong>총액</strong> <span className="cart-price"><strong>${cartTotal}</strong></span>
            </ListGroup.Item>
        </ListGroup>
    );
};

export default connector(CartItems);