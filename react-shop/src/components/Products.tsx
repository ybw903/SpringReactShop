import React, { useEffect, useState } from 'react';
import { Button, ButtonGroup, Container, Dropdown, DropdownButton, Pagination, Tab, Tabs } from 'react-bootstrap';
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

    const [sortedBy, setSortedBy] = useState('productPrice');

    const [productList, setProductList] = useState([] as Product[]);

    useEffect(()=>{
        getProducts(page.number, '')
            .then(productPage => {
                setPage(productPage.pages);
        });
    },[])

    useEffect(() => {
        getProducts(page.number, sortedBy)
            .then(productPage => {
                setProductList(productPage.productList);
        });
    }, [page.number])

    return (
        <Container>
            <ButtonGroup aria-label="Basic example" style={{paddingBottom: '1rem'}}>
                <Button >등록일순</Button>
                <Button >가격순</Button>
                <DropdownButton as={ButtonGroup} title="카테고리" id="bg-nested-dropdown">
                    <Dropdown.Item eventKey="1">책</Dropdown.Item>
                    <Dropdown.Item eventKey="2">영화</Dropdown.Item>
                </DropdownButton>
            </ButtonGroup>
            <ProductGrid productList={productList}></ProductGrid>
            <ProductPagination page={page} setPage={setPage} pageSize={5}></ProductPagination>
          
        </Container>
    )
}

export default Products;