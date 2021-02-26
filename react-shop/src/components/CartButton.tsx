import React from 'react';
import {Button } from 'react-bootstrap';
import { viewCart } from '../actions/cart';
import {History} from 'history';
import { RootState } from '../store';
import { connect, ConnectedProps } from 'react-redux';
import { RouteComponentProps, withRouter } from 'react-router-dom';

const mapDispatch = {
    onViewCart: (history: History) => viewCart(history),
};

const mapState = ({cartState}: RootState) => ({
    products: cartState.products
});

const connector = connect(
    mapState,
    mapDispatch
);

type Props = ConnectedProps<typeof connector> & RouteComponentProps;

const CartButton = ({onViewCart, products, history}: Props) => {
    return(
        <Button onClick={()=>onViewCart(history)}>My Cart</Button>
    )
}

export default withRouter(connector(CartButton));