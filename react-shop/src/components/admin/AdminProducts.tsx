import React, { useEffect, useState } from "react";
import { Button, ListGroup, Modal } from "react-bootstrap";
import { addProduct } from "../../service/admin/product";
import { getProducts } from "../../service/product";
import { Product } from "../../types/products";

const AdminProducts = () => {

    const [productList, setProductList] = useState([] as Product[]);
    const [product, setProduct] = useState({
        productName: '',
        productDescription: '',
        productPrice: 0,
        productQuantity: 0
    });
    const [showModal, setModal] = useState(false);


    const {productName, productDescription, productPrice, productQuantity} = product;

    const handleChange = (e:React.ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;
        setProduct({
            ...product,
            [name]:value
        });
        console.log(product);
    };

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
                    <div className="form-group">
                        <label>상품 이름</label>
                        <input className="form-control" type="text" name ="productName" value={productName} onChange={handleChange}></input>
                    </div>
                    <div className="form-group">
                        <label>상품 설명</label>
                        <input className="form-control" type="text" name ="productDescription" value={productDescription} onChange={handleChange}></input>
                    </div>
                    <div className="form-group">
                        <label>상품 가격</label>
                        <input className="form-control" type="number" name ="productPrice"  value={productPrice} onChange={handleChange}></input>
                    </div>
                    <div className="form-group">
                        <label>상품 수량</label>
                        <input className="form-control" type="number" name = "productQuantity" value={productQuantity} onChange={handleChange}></input>
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button className="btn btn-primary btn-lg btn-block" onClick={() => handleSubmit()}>상품추가</Button>
                </Modal.Footer>
        </Modal>
        </>
    );
}

export default AdminProducts;

function product(product: any) {
    throw new Error("Function not implemented.");
}
