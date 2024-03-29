import axios from "axios"
import { Cart } from "../types/cart"
import { Address } from "../types/checkout"

export const order = (username: string, address:Address, carts: Cart[], payment: string) => {

    //TODO : need json into product
    const productList = carts.map(el => {
        return {
            ...el,
        }
    })

    console.log({username,address,productList});
    return axios.post("/api/orders",{username, address, productList, payment});
}

export const getOrders = (username: string) => {

    return axios.get(`/api/members/${username}/orders`);
}