import React from 'react';
import {
    Navbar,Nav
} from 'react-bootstrap';

const Navigation = () =>{
    return (
        <Navbar bg="dark" variant="dark" expand="lg" fixed="top" >
            <Navbar.Brand>Shop</Navbar.Brand>
            <Nav className = "mr-auto"></Nav>
        </Navbar>
    )
}

export default Navigation;