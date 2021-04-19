import { Page } from "../types/page";
import { Product } from "../types/products";
import { ProductPage } from "../types/productsPage";

export const getProducts = async (page:Number): Promise<ProductPage> => {
    const response = await fetch(`/api/products?page=${page}&size=16`);
    const response_data = await response.json();
    const products :Product[] = await response_data._embedded.productResources;
    const pages: Page = await response_data.page;
    const productList =  products.map(el =>{
        return{
            ...el,
        };
    });

    const productPage: ProductPage = {productList, pages};
    return productPage;
}