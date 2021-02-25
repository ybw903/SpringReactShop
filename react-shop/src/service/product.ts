import { Product } from "../types/products";

export const getProducts = async (): Promise<Product[]> => {
    const response = await fetch(``);
    const products: Product[] = await response.json();
    return products.map(el =>{
        return{
            ...el
        };
    });
}