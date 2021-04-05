import React from 'react';
import {  Container } from 'react-bootstrap';
import LoginForm from './LoginForm';

const Authentication = () => {

    return (
        <Container className="auth-container">
            <LoginForm></LoginForm>
        </Container>

    )
}

export default Authentication;