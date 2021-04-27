import React from 'react';
import { Col, Container, Row } from 'react-bootstrap';
import OrderList from './OrderList';

const RecentOrder = () => {
    return(
        <Container>
            <h4>주문내역</h4>
            <Row>
                <Col>
                
                </Col>
            </Row>
            <Row>
                <Col>
                    <OrderList />
                </Col>
            </Row>
        </Container>
    )
}

export default RecentOrder;