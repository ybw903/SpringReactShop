import { Delivery } from "./delivery";
import { Product } from "./products";

export type Order = {
    delivery: Delivery
    orderDate : Date
    orderProducts : any[]
    payment: string
    statue : string
    totalPrice : number
    id : number
}