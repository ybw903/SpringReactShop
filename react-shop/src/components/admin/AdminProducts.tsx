import React, { useEffect, useState } from "react";
import { Button, ListGroup, Modal } from "react-bootstrap";
import { addProduct } from "../../service/admin/product";
import { getProducts } from "../../service/product";
import { Product } from "../../types/products";
import FormModal from "./SubmitFormModal";

const AdminProducts = () => {

    const [productList, setProductList] = useState([] as Product[]);
    const [showModal, setModal] = useState(false);
    const [product, setProduct] = useState({
        productName: '',
        productDescription: '',
        productPrice: 0,
        productQuantity: 0
    });

    const formDescription = {
        formTitle: '상품추가',
        formLabel1: '상품이름',
        formLabel2: '상품설명',
        formLabel3: '상품가격',
        formLabel4: '상품수량',
    }


    useEffect(() => {
        getProducts(0, '')
            .then(productPage => {
                setProductList(productPage.productList);
            });
    }, [])


    const handleShow = () => {
        setModal(true);
    }

    const handleClose = () => {
        setModal(false);
    }

    const handleSubmit = () => {
        console.log("hi");

        addProduct(product)
        .then((res)=>console.log(res))
        .catch()
    }

    return (
        <>
        <Button onClick={()=>handleShow()}>상품추가</Button>
        <ListGroup >
            <ListGroup.Item className="d-flex justify-content-between"><span>상품ID</span> <span>상품이름</span> <span>상품가격</span> <span>상품수량</span> <span>변경</span></ListGroup.Item>
            {
                productList.map((product, idx) => {
                    return(
                        <ListGroup.Item className="d-flex justify-content-between" key={idx}>
                            <span>{product.id}</span>
                            <span>{product.productName}</span>
                            <span>{product.productPrice}</span>
                            <span>{product.productQuantity}</span>
                            <Button>변경</Button>
                        </ListGroup.Item>
                    );
                })
            }
        </ListGroup>
        <FormModal 
            showModal={showModal}
            handleClose = {handleClose} 
            form ={product} 
            setForm ={setProduct}
            formDescription={formDescription} 
            handleSubmit = {handleSubmit}/>
        </>
    );
}

export default AdminProducts;