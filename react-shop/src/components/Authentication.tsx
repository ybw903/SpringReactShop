import React from 'react';
import {  Container } from 'react-bootstrap';
import LoginForm from './LoginForm';

const Authentication = () => {

    const onSubmit = (form: {username: string; password: string}) => {
        console.log(form);
    }
    return (
        <Container className="auth-container">
            <LoginForm onSubmit={onSubmit}></LoginForm>
        </Container>

    )
}

export default Authentication;