import React from 'react';
import {
    Navbar, Nav, Button, NavDropdown, Dropdown
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
            
            <Nav style={{marginRight:10}} >
                {loggedIn===true?
                <Dropdown>
                <Dropdown.Toggle variant="primary" id="dropdown-basic">
                    User
                    {/* <span className ="fa fa-user fa-2x text-primary remove-item"></span> */}
                </Dropdown.Toggle>
                
                <Dropdown.Menu style ={{position:'absolute'}}>
                  <Dropdown.Item><Link to ="/user">회원정보</Link></Dropdown.Item>
                  <Dropdown.Item onClick={()=>onLogout(history)}>Logout</Dropdown.Item>
                </Dropdown.Menu>
              </Dropdown>

                :
                    <Link to ="/auth"><Button>Login</Button></Link>
                }
            </Nav>
            <Nav>
                <CartButton />
            </Nav>
        </Navbar>
    )
}

export default withRouter(connector(Navigation));