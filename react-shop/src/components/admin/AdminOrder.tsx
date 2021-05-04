import axios from "axios";
import React, { useEffect, useState } from "react";
import { Button, ListGroup } from "react-bootstrap";

const AdminOrders = () => {
    const [orderList, setOrderList] = useState([] as AdminOrder[]);

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
        <ListGroup >
            <ListGroup.Item className ="d-flex justify-content-between"><span>주문ID</span> <span>주문금액</span> <span>주문상태</span> <span>상세보기</span> <span>변경</span></ListGroup.Item>
            {  
                orderList.map((order) => {
                    return <ListGroup.Item className ="d-flex justify-content-between">
                        <span>{order.id}</span> 
                        <span>{order.totalPrice}</span>
                        <span>{order.status}</span>
                        <span>상세보기</span>
                        <Button>변경</Button>
                    </ListGroup.Item>
                })
            }
        </ListGroup>
    );

}

export default AdminOrders;

type AdminOrder = {
    id: number;
    totalPrice: number;
    status: string;
}