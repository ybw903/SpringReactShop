import React, { useState} from 'react';
import { Button } from 'react-bootstrap';
import { Form } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { login } from '../service/auth';

type MyFormProps = {
    onSubmit: (form: {username: string; password: string}) => void;
}
function LoginForm({onSubmit}: MyFormProps) {
   
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
        //onSubmit(form);
        login(form.username,form.password);
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
            <div className="d-flex justify-content-between"><Link to="/registraition">회원가입하러가기</Link> <Link to="">비밀번호를 잊으셨나요?</Link></div>
        </Form>
    );
}

export default LoginForm;