import React, { useEffect, useState } from 'react';
import { Button, Container, Pagination } from 'react-bootstrap';
import { getProducts } from '../service/product';
import { Product } from '../types/products';
import ProductGrid from './ProductGrid';
import ProductPagination from './ProductPagination';

const Products = () => {

    const [page, setPage] = useState({
        number: 0,
        size: 0,
        totalElements: 0,
        totalPages: 0
    });

    const [productList, setProductList] = useState([] as Product[]);

    useEffect(()=>{
        getProducts(page.number)
            .then(productPage => {
                setPage(productPage.pages);
        });
    },[])

    useEffect(() => {
        getProducts(page.number)
            .then(productPage => {
                setProductList(productPage.productList);
        });
    }, [page.number])

    return (
        <div>
            <ProductGrid productList={productList}></ProductGrid>
            <ProductPagination page={page} setPage={setPage} pageSize={5}></ProductPagination>
          
        </div>
    )
}

export default Products;