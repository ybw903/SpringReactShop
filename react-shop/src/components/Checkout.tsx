import React from 'react';
import {
    Container,
    Col,
    Row
} from 'react-bootstrap';
import CartItems from './CartItems';
import CheckoutForm from './CheckoutForm';


const Checkout = () => {
    return(
        <Container>
            <Row>
                <Col md={4} >
                    <h4>Your Cart</h4>
                    <CartItems isEdit={false}/>
                </Col>
                <Col md={8}>
                    <CheckoutForm/>
                </Col>
            </Row>
        </Container>
    )
}

export default (Checkout);