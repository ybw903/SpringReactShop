import { Product } from "../types/products";

export const getProducts = async (): Promise<Product[]> => {
    const response = await fetch(`/api/products?page=0&size=20`);

    const response_data = await response.json();

    console.log(response_data);
    const products :Product[] = await response_data._embedded.productResources;
    
    console.log(products);

    return products.map(el =>{
        return{
            ...el,
            orderQuantity:1
        };
    });
}