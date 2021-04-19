import { ADD_TO_CART,
    REMOVE_FROM_CART,
    VIEW_CART,
    CHANGE_QUANTITY_OF_CART,
     CartActionTypes } from "../types/cart";
import { Product } from "../types/products";
import {History} from 'history';

export const addToCart = (product: Product) : CartActionTypes => {
    return {
        type: ADD_TO_CART,
        product
    }
};

export const removeFromCart = (location: number): CartActionTypes => {
    return {
        type: REMOVE_FROM_CART,
        location
    }
};

export const changeQuantityOfCart = (location:number, isAddOrMinus: boolean) : CartActionTypes => {
    return {
        type: CHANGE_QUANTITY_OF_CART,
        location,
        isAddOrMinus
    }
}

export const viewCart = (history: History): CartActionTypes => {
    history.push('/cart');
    return {
        type: VIEW_CART
    }
}