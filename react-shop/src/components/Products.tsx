import React, {useEffect, useState} from 'react';
import { Button, Container, Pagination } from 'react-bootstrap';
import { getProducts } from '../service/product';
import { Product } from '../types/products';
import ProductGrid from './ProductGrid';

const Products = () => {

    const [page, setPage] = useState({
        number:0,
        size: 0,
        totalElements:0,
        totalPages:0
    });
    

    const [productList, setProductList] = useState([] as Product[]);

    useEffect(()=>{
        
        getProducts(page.number)
       .then(productPage =>{
           setPage(productPage.pages);
           setProductList(productPage.productList);
        });
        return;
    },[page.number])

    
    const pageFirst = () => {setPage({...page,number:0})}
    const pageLast = () => { setPage({...page,number: page.totalPages-1});}
    const pageNext = () => {
        const changeNum = page.number===page.totalPages-1?page.number:page.number+1;
        setPage(
        {
            ...page,
            number:changeNum
        });
    }
    const pagePrev = () => {
        const changeNum = page.number===0?page.number:page.number-1
        setPage(
        {   
            ...page,
            number:changeNum
        });
    }
    console.log(page.number);
    return(
        <div>
        <ProductGrid productList={productList}></ProductGrid>
        
        <Pagination className="justify-content-center">
            <Pagination.First onClick={pageFirst}/>
            <Pagination.Prev onClick={pagePrev}/>
            {
                
                Array.from({length:(page.number+5>page.totalPages?page.totalPages-page.number:5)},(_,v)=>v+page.number).map(
                    (v,i)=>{    
                        
                        if(v===page.number) return (<Pagination.Item active onClick={()=>setPage({...page,number:v})}>{v+1}</Pagination.Item>);
                        return (<Pagination.Item onClick={()=>setPage({...page,number:v})}>{v+1}</Pagination.Item>);
                })
            }
            <Pagination.Ellipsis />
            <Pagination.Next onClick={pageNext}/>
            <Pagination.Last onClick={pageLast}/>
        </Pagination>
        </div>
    )
}

export default Products;