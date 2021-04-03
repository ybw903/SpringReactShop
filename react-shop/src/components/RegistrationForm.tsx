import React, { useState } from 'react';
import { Button, Container } from 'react-bootstrap';
import { Form } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { login } from '../service/auth';

type MyFormProps = {
    onSubmit: (form: { username: string; password: string }) => void;
}
function RegistrationFrom() {

    const [form, setForm] = useState({
        username: '',
        password: ''
    });

    const { username, password } = form;

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setForm({
            ...form,
            [name]: value
        });
    };

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        //onSubmit(form);
        login(form.username, form.password);
        setForm({
            username: '',
            password: ''
        });
    };

    return (
        <Container>
            <Form onSubmit={handleSubmit} className="login-form">
                <h3 className="form-title">Sign-up</h3>
                <div className="form-group">
                    <label>Username</label>
                    <input type="text" className="form-control" placeholder="이름을 입력하세요"
                        name="username" value={username} onChange={handleChange} />
                </div>

                <div className="form-group">
                    <label>Password</label>
                    <input type="password" className="form-control" placeholder="비밀번호를 입력하세요"
                        name="password" value={password} onChange={handleChange} />
                </div>
                <Button type="submit" className="btn btn-primary btn-lg btn-block">회원가입</Button>
                <div className="d-flex justify-content-between"><Link to="/auth">로그인하러가기</Link> <Link to="">비밀번호를 잊으셨나요?</Link></div>
            </Form>
        </Container>
    );
}

export default RegistrationFrom;