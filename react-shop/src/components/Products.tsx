import React, { useEffect, useState } from 'react';
import { Button, Container, Pagination } from 'react-bootstrap';
import { getProducts } from '../service/product';
import { Product } from '../types/products';
import ProductGrid from './ProductGrid';

const Products = () => {

    const [page, setPage] = useState({
        number: 0,
        size: 0,
        totalElements: 0,
        totalPages: 0
    });

    const [pageArr, setPageArr] = useState([0,1,2,3,4] as number[]);

    const [productList, setProductList] = useState([] as Product[]);

    useEffect(() => {
        getProducts(page.number)
            .then(productPage => {
                setProductList(productPage.productList);
        });
    }, [page.number])


    const pageFirst = () => { 
        const changePageNum = page.number - 5;
        setPage({ ...page, number: changePageNum });
        setPageArr(pageArr.map((v,i)=>v-5));
        console.log(pageArr);
     }
    const pageLast = () => {
        const changePageNum = page.number + 5;
        setPage({ ...page, number: changePageNum });
        setPageArr(pageArr.map((v,i)=>v+5));
        console.log(pageArr);
    }

    return (
        <div>
            <ProductGrid productList={productList}></ProductGrid>

            <Pagination className="justify-content-center">
                <Pagination.First onClick={pageFirst} />
                {
                    pageArr.map(
                        (v, i) => {
                            if (v === page.number) return (<Pagination.Item active onClick={() => setPage({ ...page, number: v })}>{v + 1}</Pagination.Item>);
                            return (<Pagination.Item onClick={() => setPage({ ...page, number: v })}>{v + 1}</Pagination.Item>);
                        })
                }
                <Pagination.Last onClick={pageLast} />
            </Pagination>
        </div>
    )
}

export default Products;