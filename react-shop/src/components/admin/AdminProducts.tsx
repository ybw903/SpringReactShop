import React, { useEffect, useState } from "react";
import { ListGroup } from "react-bootstrap";
import { getProducts } from "../../service/product";
import { Product } from "../../types/products";

const AdminProducts = () => {

    const [productList, setProductList] = useState([] as Product[]);

    useEffect(() => {
        getProducts(0,'')
            .then(productPage => {
                setProductList(productPage.productList);
        });
    }, [])


    return(
        <ListGroup >
            <ListGroup.Item className ="d-flex justify-content-between"><span>상품ID</span> <span>상품이름</span> <span>상품가격</span> <span>상품수량</span></ListGroup.Item>
            {  
                productList.map((product) => {
                    return <ListGroup.Item className ="d-flex justify-content-between"><span>{product.id}</span><span>{product.productName}</span>  <span>{product.productPrice}</span> <span>{product.productQuantity}</span></ListGroup.Item>
                })
            }
        </ListGroup>
    );
}

export default AdminProducts;