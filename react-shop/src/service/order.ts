import axios from "axios"
import { Cart } from "../types/cart"
import { Address } from "../types/checkout"

export const order = (username: string, address:Address, carts: Cart[]) => {

    //TODO : need json into product
    const productList = carts.map(el => {
        return {
            ...el,
            orderPrice: el.orderPrice,
            orderQuantity: el.orderQuantity
        }
    })
    return axios.post("/api/orders",{username, address, productList});
}