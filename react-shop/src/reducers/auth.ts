import { AuthActionTypes, AuthState, LOGIN_FAILURE, LOGIN_REQUEST, LOGIN_SUCCESS } from "../types/auth";

const initialState :AuthState = {
    loggedIn: false,
    loggingIn: false,
    user: undefined,
    error: null
};

const authReducer = (state = initialState, action: AuthActionTypes): AuthState => {
    switch(action.type) {
        case LOGIN_REQUEST:

        case LOGIN_SUCCESS:
            
        case LOGIN_FAILURE:
            
        default:
            return state;
    }
}

export default authReducer;