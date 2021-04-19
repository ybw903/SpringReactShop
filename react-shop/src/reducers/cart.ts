import { act } from "react-dom/test-utils";
import { ADD_TO_CART, CartActionTypes, CartState, REMOVE_FROM_CART, CHANGE_QUANTITY_OF_CART, VIEW_CART } from "../types/cart";

const initialState : CartState = {
    carts: [],
}

const cartReducer = (state = initialState, action: CartActionTypes): CartState => {
    switch(action.type) {
        case ADD_TO_CART:
            if(state.carts.some(v=>v.product.id === action.product.id))
                return {carts: state.carts};
            return {
                carts: [...state.carts,
                    {
                        product: action.product, 
                        orderPrice:action.product.productPrice,
                        orderQuantity: 1
                    }
                ]
            };
        case REMOVE_FROM_CART:
            return {
                carts: state.carts.filter((_,index) => index!==action.location),
            };
        case VIEW_CART:
            return {
                carts: state.carts,
            };
        case CHANGE_QUANTITY_OF_CART:
            return {
                carts: state.carts.map(
                    (value,index) => {
                        if(index===action.location){
                            if((value.orderQuantity===1&&!action.isAddOrMinus)
                                ||(value.orderQuantity===value.product.productQuantity&&action.isAddOrMinus)) return value;
                            return {
                                ...value,
                                orderQuantity: value.orderQuantity+(action.isAddOrMinus?1:-1)
                            }
                        }
                        return value;
                    } ),
            }
        default:
            return state;
    }
}

export default cartReducer;