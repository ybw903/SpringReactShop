import { Product } from "./products";

export const ADD_TO_CART = 'ADD_TO_CART';
export const REMOVE_FROM_CART = 'REMOVE_FROM_CART';
export const VIEW_CART = 'VIEW_CART';
export const CHECKOUT_CART = 'CHECKOUT_CART';

export interface AddToCartAction {
    type: typeof ADD_TO_CART,
    product: Product
}

export interface RemoveFromCartAction {
   type: typeof REMOVE_FROM_CART,
   location: number
}

export interface ChangeQuantityofCartAction {
   
}

export interface ViewCartAction {
   type: typeof VIEW_CART
}

export interface CheckoutCartAction {
   type: typeof CHECKOUT_CART
}

export type CartActionTypes = AddToCartAction | RemoveFromCartAction | ViewCartAction | CheckoutCartAction;

export type Cart = {
   product : Product
   orderQuantity: number
   orderPrice: number
}

export interface CartState {
    carts : Cart[]
}