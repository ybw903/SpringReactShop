import { ADD_TO_CART, CartActionTypes, CartState, REMOVE_FROM_CART, VIEW_CART } from "../types/cart";

const initialState : CartState = {
    products: [],
}

const cartReducer = (state = initialState, action: CartActionTypes): CartState => {
    switch(action.type) {
        case ADD_TO_CART:

            let idx: number = state.products.findIndex(p=>p.id===action.product.id);
            if(idx!==-1){
                state.products[idx].quantity+=1;
                return {
                    products: state.products
                };
            } else {
                return {
                    products: [...state.products, action.product],
                };
            }           
             
        case REMOVE_FROM_CART:
            return {
                products: state.products.filter((_,index) => index!==action.location),
            };
        case VIEW_CART:
            return {
                products: state.products,
            };
        default:
            return state;
    }
}

export default cartReducer;