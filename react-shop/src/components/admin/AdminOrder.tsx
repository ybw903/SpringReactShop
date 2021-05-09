import axios from "axios";
import { useEffect, useState } from "react";
import { Accordion, Button, Card, Col, Form, ListGroup } from "react-bootstrap";

type AdminOrder = {
    id: number;
    totalPrice: number;
    status: string;
}

const AdminOrders = () => {
    const [orderList, setOrderList] = useState([] as any[]);

    useEffect(() => {
        axios.get(`/api/orders`)
        .then((res) => {
            return res.data._embedded.orderResources;
        })
        .then(
            (orderResources) => {
                setOrderList(orderResources);
                console.log(orderResources);
            }
        )
        .catch(
            err => console.log(err)
        );
    }, [])


    return(
        <>
        <ListGroup >
            <ListGroup.Item className ="d-flex justify-content-between"><span>주문ID</span> <span>주문금액</span> <span>주문상태</span> <span>상세보기</span></ListGroup.Item>
            {  
                orderList.map((order,idx) => {
                    return(
                        <ListGroup.Item >
                            <Accordion>
                                <Card>
                                    <Card.Header  className ="d-flex justify-content-between">
                                        <span>{order.id}</span> 
                                        <span>{order.totalPrice}</span>
                                        <span>{order.status}</span>
                                        <Accordion.Toggle as={Button} variant="link" eventKey={`${idx}`}>
                                            상세보기
                                        </Accordion.Toggle>
                                    </Card.Header>
                                    <Accordion.Collapse eventKey ={`${idx}`}>
                                        <Card.Body>
                                            <Form>
                                                <Form.Row>
                                                    <Form.Group as={Col} controlId="formMember">
                                                        <Form.Label>주문 회원</Form.Label>
                                                        <Form.Control />
                                                    </Form.Group>
                                                </Form.Row>

                                                <Form.Row>
                                                    <Form.Group as={Col} controlId="formProduct">
                                                        <Form.Label>결제 상품</Form.Label>
                                                        <Form.Control />
                                                    </Form.Group>
                                                </Form.Row>

                                                <Form.Row>
                                                    <Form.Group as={Col} controlId="formPhone">
                                                        <Form.Label>배송지 연락처</Form.Label>
                                                        <Form.Control />
                                                    </Form.Group>

                                                    <Form.Group as={Col} controlId="formStreet">
                                                        <Form.Label>배송지 도로주소</Form.Label>
                                                        <Form.Control />
                                                    </Form.Group>

                                                    <Form.Group as={Col} controlId="formZipCode">
                                                        <Form.Label>배송지 우편번호</Form.Label>
                                                        <Form.Control />
                                                    </Form.Group>
                                                </Form.Row>

                                                <Form.Row>
                                                    <Form.Group as={Col} controlId="formPayment">
                                                        <Form.Label>결제 수단</Form.Label>
                                                        <Form.Control />
                                                    </Form.Group>
                                                    <Form.Group as={Col} controlId="formTotalPrice">
                                                        <Form.Label>결제 총액</Form.Label>
                                                        <Form.Control />
                                                    </Form.Group>
                                                    <Form.Group as={Col} controlId="formDate">
                                                        <Form.Label>결제 일자</Form.Label>
                                                        <Form.Control />
                                                    </Form.Group>
                                                </Form.Row>
                                            </Form>
                                        </Card.Body>
                                    </Accordion.Collapse>
                                </Card>
                            </Accordion>
                        </ListGroup.Item>
                    );
                    
                })
            }
        </ListGroup>
        </>
    );

}

export default AdminOrders;