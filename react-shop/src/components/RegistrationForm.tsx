import React, { useState } from 'react';
import { Button, Container, Modal } from 'react-bootstrap';
import { Form } from 'react-bootstrap';
import { connect, ConnectedProps } from 'react-redux';
import { Link, RouteComponentProps, withRouter } from 'react-router-dom';
import { register } from '../actions/auth';
import { RootState } from '../store';

const mapState = ({authState}: RootState) =>({
    authState: authState
});

const mapDispatch = {
   onRegisetr : (username:string, password: string) => register(username, password)
}

const connector = connect(
    mapState,
    mapDispatch,
);

type Props = ConnectedProps<typeof connector> & RouteComponentProps;


function RegistrationFrom({authState, onRegisetr}:Props) {

    const [form, setForm] = useState({
        username: '',
        password: ''
    });
    const { username, password } = form;

    const [showModal, setModal] = useState(false);
    const [resultMessage , setMessage] = useState('');

    const handleShow = () => {
        setModal(true);
    }

    const handleClose = () => {
        setModal(false);
    }

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setForm({
            ...form,
            [name]: value
        });
    };

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        onRegisetr(username,password);

        setMessage((authState.error == null?'성공':'실패'));
        handleShow();

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
                <Button type="submit" className="btn btn-danger btn-lg btn-block">회원가입</Button>
                <p className="text-center"><Link to="/auth">로그인하러가기</Link></p>
            </Form>
            <Modal show={showModal} onHide={handleClose}>
                <Modal.Header closeButton>
                </Modal.Header>
                <Modal.Body>
                    {resultMessage}
                </Modal.Body>
                <Modal.Footer>
                    {resultMessage=='성공'?
                    <Link to="/auth"><Button className="btn btn-primary btn-lg btn-block">로그인하러가기</Button></Link>
                    :<Button className="btn btn-primary btn-lg btn-block" onClick={()=>handleClose()}>닫기</Button>}
                </Modal.Footer>
            </Modal>
        </Container>
    );
}

export default withRouter(connector(RegistrationFrom));