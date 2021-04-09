import axios from "axios"
import { Address } from "../types/checkout"
import { Product } from "../types/products"

export const order = (username: string, address:Address, products: Product[]) => {

    const productList = products.map(el => {
        return {
            ...el,
            orderPrice: el.productPrice
        }
    })
    return axios.post("/api/orders",{username, address, productList});
}