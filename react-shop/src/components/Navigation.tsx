import React from 'react';
import {
    Navbar, Nav, Button
} from 'react-bootstrap';
import { Link } from 'react-router-dom';
import CartButton from './CartButton';
import {History} from 'history';

const Navigation = () => {
    return (
        <Navbar bg="dark" variant="dark" expand="lg" fixed="top" >
            <Navbar.Brand><Link to="/">Shop</Link></Navbar.Brand>
            <Nav className="mr-auto">
            </Nav>
            <Nav>
                <CartButton />
            </Nav>
            <Nav style={{marginLeft: "10px"}}>
                <Link to ="/auth"><Button>Login</Button></Link>
            </Nav>
        </Navbar>
    )
}

export default Navigation;