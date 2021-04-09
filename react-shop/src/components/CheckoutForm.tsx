import React, {useState} from 'react';
import { Col, Form, Row } from 'react-bootstrap';
import { connect, ConnectedProps } from 'react-redux';
import { useHistory } from 'react-router';
import { order } from '../service/order';
import { RootState } from '../store';
import { Address } from '../types/checkout';

const mapState = ({authState, cartState}: RootState) =>({
    user : authState.user,
    products : cartState.products
});

const mapDispatch = {
   
}

const connector = connect(
    mapState,
    mapDispatch,
);

type PropsFromRedux = ConnectedProps<typeof connector>

type Props = PropsFromRedux& {
}


const CheckoutForm = ({user, products}:Props) => {

    const username = user?.username?user?.username:'';
    const [phone, setPhone] = useState(user?.phone?user?.phone:'');
    const [zipcode, setZipcode] = useState(user?.zipcode?user?.zipcode:'');
    const [street, setStreet] = useState(user?.street?user?.street:'');
    const [payment, setPayment] = useState('');
    const history = useHistory();

    const onSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        const address: Address = {
            zipcode,
            street,
            phone
        };
        order(username,address,products)
        .then((res)=>console.log(res))
        .catch((err) =>{
            console.log(err);
        });
    }

    return(
        <div>
        <h4>Billing Address</h4>
        <Form onSubmit={onSubmit}>
            <div className='fs-block'>
                <Row>
                    <Col>
                        <label htmlFor='name'>이름</label>
                        <input type='text' className='form-control' id='name' defaultValue={username} readOnly></input>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <label htmlFor='phone'>Phone <span className='text-muted'></span></label>
                        <input type='text' className='form-control' id='phone' placeholder='ex) 010-1234-5678'
                        defaultValue={phone} onChange={e => setPhone(e.target.value)}></input>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <label htmlFor='zipcode'>우편번호</label>
                        <input type='text' className='form-control' id='zipcode' placeholder='ex) 000000'
                        defaultValue={zipcode} onChange={e => setZipcode(e.target.value)}></input>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <label htmlFor='street'>주소</label>
                        <input type='text' className='form-control' id='street' placeholder='ex) 서울시 강남구 테헤란로'
                        defaultValue={street} onChange={e => setStreet(e.target.value)}></input>
                    </Col>
                </Row>
            </div>
            <div className='fs-block'>
                <Row>
                    <Col>
                        <h4>Payment</h4>
                        <div className='custom-control custom-radio'>
                            <input id="credit" type="radio" className="custom-control-input" name="payment"value="credit"  onChange={e=>setPayment(e.target.value)}></input>
                            <label className="custom-control-label" htmlFor="credit">신용카드</label>
                        </div>
                        <div className='custom-control custom-radio'>
                            <input id="debit" type="radio" className="custom-control-input" name="payment"value="debit" onChange={e=>setPayment(e.target.value)}></input>
                            <label className="custom-control-label" htmlFor="debit">계좌이체</label>
                        </div>
                        <div className='custom-control custom-radio'>
                            <input id="paypal" type="radio" className="custom-control-input" name="payment"value="paypal" onChange={e=>setPayment(e.target.value)}></input>
                            <label className="custom-control-label" htmlFor="paypal">페이팔</label>
                        </div>
                    </Col>
                </Row>
            </div>
            <Row>
                <Col>
                <hr/>
                <button className="btn btn-primary btn-lg btn-block" type="submit" >결제</button>
                </Col>
            </Row>
        </Form>
        </div>
    )
}

export default connector(CheckoutForm);