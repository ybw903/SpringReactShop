import {combineReducers} from 'redux';
import authReducer from './auth';
import cartReducer from './cart';

const rootReducer = combineReducers({
    cartState : cartReducer,
    authState : authReducer
});

export default rootReducer;