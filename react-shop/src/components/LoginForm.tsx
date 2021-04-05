import React, { useState} from 'react';
import { Button } from 'react-bootstrap';
import { Form } from 'react-bootstrap';
import { connect, ConnectedProps } from 'react-redux';
import { Link, RouteComponentProps, withRouter } from 'react-router-dom';
import {History} from 'history';
import { login, loginRequest } from '../actions/auth';
import { RootState } from '../store';

const mapState = ({authState}: RootState) =>({
    authState: authState
});

const mapDispatch = {
   onLogin: (username:string, password: string, history: History) => login(username, password, history)
}

const connector = connect(
    mapState,
    mapDispatch,
);

type Props = ConnectedProps<typeof connector> & RouteComponentProps;


function LoginForm({authState, onLogin, history}: Props) {
   
    const [form, setForm] = useState({
        username: '',
        password: ''
    });

    const {username, password} = form;

    const handleChange = (e:React.ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;
        setForm({
            ...form,
            [name]:value
        });
    };

    const handleSubmit = (e:React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        onLogin(username, password, history);
        setForm({
            username: '',
            password: ''
        });
    };

    return(
        <Form onSubmit={handleSubmit} className="login-form">
            <h3 className="form-title">Log in</h3>
            <div className="form-group">
                <label>Username</label>
                <input type="text" className="form-control" placeholder="이름을 입력하세요"
                 name="username" value={username} onChange={handleChange}/>
            </div>

            <div className="form-group">
                <label>Password</label>
                <input type="password" className="form-control" placeholder="비밀번호를 입력하세요" 
                name="password" value={password} onChange={handleChange}/>
            </div>
            <Button type="submit" className="btn btn-primary btn-lg btn-block">로그인</Button>
            <p className="text-center"><Link to="/registraition">회원가입하러가기</Link></p>
        </Form>
    );
}

export default withRouter(connector(LoginForm));