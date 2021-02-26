import {combineReducers} from 'redux';
import cartReducer from './cart';

const rootReducer = combineReducers({
    cartState : cartReducer,
});

export default rootReducer;