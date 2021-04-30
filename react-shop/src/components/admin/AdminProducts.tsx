import React, { useEffect, useState } from "react";
import { Button, ListGroup, Modal } from "react-bootstrap";
import { getProducts } from "../../service/product";
import { Product } from "../../types/products";

const AdminProducts = () => {

    const [productList, setProductList] = useState([] as Product[]);
    const [showModal, setModal] = useState(false);

    const handleShow = () => {
        setModal(true);
    }

    const handleClose = () => {
        setModal(false);
    }

    useEffect(() => {
        getProducts(0, '')
            .then(productPage => {
                setProductList(productPage.productList);
            });
    }, [])


    return (
        <>
        <Button onClick={()=>handleShow()}>상품추가</Button>
        <ListGroup >
            <ListGroup.Item className="d-flex justify-content-between"><span>상품ID</span> <span>상품이름</span> <span>상품가격</span> <span>상품수량</span></ListGroup.Item>
            {
                productList.map((product, idx) => {
                    return(
                        <ListGroup.Item className="d-flex justify-content-between" key={idx}>
                            <span>{product.id}</span>
                            <span>{product.productName}</span>
                            <span>{product.productPrice}</span>
                            <span>{product.productQuantity}</span>
                        </ListGroup.Item>
                    );
                })
            }
        </ListGroup>
        <Modal show={showModal} onHide={handleClose}>
                <Modal.Header closeButton>
                    상품추가폼
                </Modal.Header>
                <Modal.Body>
                </Modal.Body>
                <Modal.Footer>
                    <Button className="btn btn-primary btn-lg btn-block" onClick={()=>handleClose()}>닫기</Button>
                </Modal.Footer>
        </Modal>
        </>
    );
}

export default AdminProducts;