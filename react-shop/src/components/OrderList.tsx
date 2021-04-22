import React, { useEffect, useState } from 'react';
import { Button, ListGroup, Table } from 'react-bootstrap';
import { connect, ConnectedProps } from 'react-redux';
import { getOrders } from '../service/order';
import { RootState } from '../store';
import { Order } from '../types/order';

const mapState = ({ authState }: RootState) => ({
    user: authState.user
});

const mapDispatch = {

}

const connector = connect(
    mapState,
    mapDispatch
);

type PropsFromRedux = ConnectedProps<typeof connector>

type Props = PropsFromRedux & {

}

const OrderList = ({ user }: Props) => {

    const [orders, setOrders] = useState([] as Order[])

    useEffect(() => {
        getOrders(user?.username ? user?.username : '')
            .then((res) => res.data._embedded.orderResources)
            .then(
                (orderResources) => {
                    setOrders(orderResources);

                    console.log(orderResources);
                }
            )
    }, [])

    return (
        <Table striped bordered hover>
            <thead>
                <tr>
                    <th>주문번호</th>
                    <th>상품정보</th>
                    <th>주문총액</th>
                    <th>주문일자</th>
                    <th>주문취소</th>
                </tr>
            </thead>
            <tbody>
                {
                    orders.map((order,i)=>(
                        <tr>
                            <td>#{order.id}</td>
                            <td>{order.orderProducts[0].product.productName}  {order.orderProducts.length>1?` 외 ${order.orderProducts.length}건`:''}</td>
                            <td>${order.totalPrice}</td>
                            <td>{order.orderDate}</td>
                            <td><Button>취소</Button></td>
                        </tr>
                    ))
                }
            </tbody>
        </Table>
    )
}

export default connector(OrderList);