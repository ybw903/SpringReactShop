import axios from "axios"
import { Product } from "../types/products"

export const order = (username: string, productList: Product[]) => {

    const productRequsetList = productList.map(el => {
        return {
            ...el,
            orderQuantity: el.quantity,
            orderPrice: el.productPrice
        }
    })
    return axios.post("/api/authenticate/login",{username, productRequsetList});
}