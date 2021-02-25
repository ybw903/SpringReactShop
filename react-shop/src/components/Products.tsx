import React, {useEffect, useState} from 'react';
import { getProducts } from '../service/product';
import { Product } from '../types/products';
import ProductGrid from './ProductGrid';

const Products = () => {

    const [productList, setProductList] = useState([] as Product[]);

    useEffect(()=>{
        getProducts().then(products => setProductList(products));
    })


    return(
        <ProductGrid></ProductGrid>
    )
}

export default Products;