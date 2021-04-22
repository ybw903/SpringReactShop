import { Button } from 'react-bootstrap';
import { Col, Container, Row } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import CartItems from './CartItems';

const Cart = () => {
    return(
        <Container className="cart-container">
            <Row>
                <Col>
                <span style={{float: 'right'}}><Link to="/">Keep Shopping</Link></span>
                <h4>Your Cart</h4>
                </Col>
            </Row>
            <Row>
                <Col>
                <CartItems isEdit={true}/>
                <Link to="/checkout" style={{float:'right', padding:'1rem'}}>
                    <Button variant="primary">Continue</Button>
                </Link>
                </Col>
            </Row>
        </Container>
    )
}

export default Cart;