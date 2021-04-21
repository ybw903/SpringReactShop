import { Delivery } from "./delivery";
import { Product } from "./products";

export type Order = {
    delivery: Delivery
    orderDate : Date
    orderProducts : any[]
    statue : string
    totalPrice : number
    id : number
}