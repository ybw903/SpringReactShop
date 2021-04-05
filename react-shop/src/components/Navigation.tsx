import React from 'react';
import {
    Navbar, Nav, Button
} from 'react-bootstrap';
import { Link, RouteComponentProps, withRouter } from 'react-router-dom';
import CartButton from './CartButton';
import {History} from 'history';
import { RootState } from '../store';
import { connect, ConnectedProps } from 'react-redux';
import { Logout } from '../actions/auth';

const mapState = ({authState}: RootState) =>({
    loggedIn : authState.loggedIn
});

const mapDispatch = {
    onLogout : (history: History) =>Logout(history)
}

const connector = connect(
    mapState,
    mapDispatch,
);

type Props = ConnectedProps<typeof connector> & RouteComponentProps;

const Navigation = ({loggedIn, onLogout, history}:Props) => {
    return (
        <Navbar bg="dark" variant="dark" expand="lg" fixed="top" >
            <Navbar.Brand><Link to="/">Shop</Link></Navbar.Brand>
            <Nav className="mr-auto">
            </Nav>
            <Nav>
                <CartButton />
            </Nav>
            <Nav style={{marginLeft: "10px"}}>
                {loggedIn===true?
                <Button onClick={()=>onLogout(history)}>Logout</Button>:
                <Link to ="/auth"><Button>Login</Button></Link>}
            </Nav>
        </Navbar>
    )
}

export default withRouter(connector(Navigation));