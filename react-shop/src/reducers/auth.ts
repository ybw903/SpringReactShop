import { AuthActionTypes, AuthState, LOAD_USER, LOGIN_FAILURE, LOGIN_REQUEST, LOGIN_SUCCESS, LOG_OUT, REGISTER_FAILURE, REGISTER_REQUEST, REGISTER_SUCCESS } from "../types/auth";

const initialState :AuthState = {
    loggedIn: false,
    isFetching: false,
    user: undefined,
    error: null
};

const authReducer = (state = initialState, action: AuthActionTypes): AuthState => {
    switch(action.type) {
        case LOGIN_REQUEST:
            return {
                ...state,
                isFetching : true,
                error : null,
            };
        case LOGIN_SUCCESS:
            return {
                ...state,
                loggedIn : true,
                isFetching : false,
                user : action.user
            };
        case LOGIN_FAILURE:
            return {
                ...state,
                isFetching : false,
                error : action.error
            }
        case LOG_OUT:
            return {
                ...state,
                loggedIn : false,
                user: undefined,
            }
        case REGISTER_REQUEST:
            return {
                ...state,
                isFetching: true,
                error : null,
            }
        case REGISTER_SUCCESS:
            return {
                ...state,
                isFetching:false,
            }
        case REGISTER_FAILURE:
            return {
                ...state,
                isFetching:false,
                error : action.error
            }
        case LOAD_USER:
            return {
                ...state,
                user : action.user
            }
        default:
            return state;
    }
}

export default authReducer;