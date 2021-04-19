import { ADD_TO_CART, CartActionTypes, CartState, REMOVE_FROM_CART, VIEW_CART } from "../types/cart";

const initialState : CartState = {
    carts: [],
}

const cartReducer = (state = initialState, action: CartActionTypes): CartState => {
    switch(action.type) {
        case ADD_TO_CART:
            return {
                carts: [...state.carts,
                    {
                        product: action.product, 
                        orderPrice:action.product.productPrice,
                        orderQuantity: 1
                    }
                ]
            }    
        case REMOVE_FROM_CART:
            return {
                carts: state.carts.filter((_,index) => index!==action.location),
            };
        case VIEW_CART:
            return {
                carts: state.carts,
            };
        default:
            return state;
    }
}

export default cartReducer;