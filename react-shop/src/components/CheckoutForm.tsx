import React, {useState} from 'react';
import { Col, Form, Row } from 'react-bootstrap';

const CheckoutForm = () => {
    return(
        <div>
        <h4>Billing Address</h4>
        <Form>
            <div className='fs-block'>
                <Row>
                    <Col>
                        <label htmlFor='name'>이름</label>
                        <input type='text' className='form-control' id='name'></input>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <label htmlFor='email'>Email <span className='text-muted'>(선택사항)</span></label>
                        <input type='text' className='form-control' id='email' placeholder='you@example.com'></input>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <label htmlFor='address'>주소</label>
                        <input type='text' className='form-control' id='address' placeholder='you@example.com'></input>
                    </Col>
                </Row>
            </div>
            <div className='fs-block'>
                <Row>
                    <Col>
                        <h4>Payment</h4>
                        <div className='custom-control custom-radio'>
                            <input id="credit" type="radio" className="custom-control-input"></input>
                            <label className="custom-control-label" htmlFor="credit">신용카드</label>
                        </div>
                        <div className='custom-control custom-radio'>
                            <input id="debit" type="radio" className="custom-control-input"></input>
                            <label className="custom-control-label" htmlFor="debit">계좌이체</label>
                        </div>
                        <div className='custom-control custom-radio'>
                            <input id="paypal" type="radio" className="custom-control-input"></input>
                            <label className="custom-control-label" htmlFor="paypal">페이팔</label>
                        </div>
                    </Col>
                </Row>
            </div>
            <Row>
                <Col>
                <hr/>
                <button className="btn btn-primary btn-lg btn-block" type="submit">결제</button>
                </Col>
            </Row>
        </Form>
        </div>
    )
}

export default CheckoutForm;