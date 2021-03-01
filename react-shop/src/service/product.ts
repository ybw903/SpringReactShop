import { Product } from "../types/products";

export const getProducts = async (): Promise<Product[]> => {
    const response = await fetch(`/api/products`);

    const response_data = await response.json();

    console.log(response_data);
    const products :Product[] = await response_data._embedded.products
    
    console.log(products);

    return products.map(el =>{
        return{
            ...el,
            quantity:1
        };
    });
}