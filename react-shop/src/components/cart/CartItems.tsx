import React from 'react';
import { ListGroup } from 'react-bootstrap';
import { connect, ConnectedProps } from 'react-redux';
import { changeQuantityOfCart, removeFromCart } from '../../actions/cart';
import { RootState } from '../../store';

const mapState = ({cartState}: RootState) =>({
    carts: cartState.carts
});

const mapDispatch = {
    onRemoveFromCart: (location: number) => removeFromCart(location),
    onChangeQuantityOfCart: (location:number, isAddOrMinus: boolean) => changeQuantityOfCart(location, isAddOrMinus)
}

const connector = connect(
    mapState,
    mapDispatch,
);

type PropsFromRedux = ConnectedProps<typeof connector>

type Props = PropsFromRedux& {
    isEdit: boolean
}

const CartItems = ({isEdit, carts, onRemoveFromCart, onChangeQuantityOfCart}: Props) => {
    const cartTotal = carts.reduce((total,el) =>total+el.orderPrice*el.orderQuantity,0);
    return(
        <ListGroup>
            { carts.map((cart, i) =>(
                <ListGroup.Item key={i}>
                    {cart.product.productName}
                    
                    <span className="cart-price">${cart.orderPrice+'  '}
                    <span className="fa fa-minus-circle" onClick={()=>onChangeQuantityOfCart(i,false)}></span>
                    <span className="text-muted">{'  '+ cart.orderQuantity}개 </span>
                    <span className="fa fa-plus-circle" onClick={()=>onChangeQuantityOfCart(i,true)}></span>
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