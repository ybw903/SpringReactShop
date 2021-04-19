import {LOGIN_REQUEST, LOGIN_SUCCESS, LOGIN_FAILURE, LOG_OUT, AuthActionTypes, REGISTER_REQUEST, REGISTER_SUCCESS, REGISTER_FAILURE, LOAD_USER} from '../types/auth';
import { User } from '../types/user';
import {History} from 'history';
import axios from 'axios';
import { authLogin, authSignUp, setUpAxiosInterceptors } from '../service/auth';

export function login(username:string, password:string, history: History) {
    return (dispatch: (authAction: AuthActionTypes) => void) => {
        dispatch(loginRequest());

        authLogin(username, password)
        .then((response) =>{
            let token: string = response.data.jwttoken;
            console.log(token);
            
            // TODO : Refactor if need
            localStorage.setItem('token', token);
            localStorage.setItem('authUser', username);

            dispatch(LoginSuccess({username,phone:undefined,zipcode:undefined,street:undefined}));
            history.push('/');
        }).catch((error)=>{
            console.log(error);
            dispatch(LoginFauilure(error));
        }) 
    }
}

export function register(username:string, password: string) {
    return (dispatch: (authAction: AuthActionTypes)=>void) => {
        dispatch(RegisterRequest());
        authSignUp(username, password)
        .then((response) => {
            dispatch(RegisterSuccess());
        })
        .catch((error)=>{
            dispatch(RegisterFailure(error));
        })
    }
}

export const loginRequest = ():AuthActionTypes => {
    return {
        type: LOGIN_REQUEST
    };
}

export const LoginSuccess = (user: User):AuthActionTypes => {
    setUpAxiosInterceptors();
    return {
        type: LOGIN_SUCCESS,
        user: user
    };
}

export const LoginFauilure = (error: any):AuthActionTypes => {
    return {
        type: LOGIN_FAILURE,
        error: error
    };
}

export const Logout = (history: History):AuthActionTypes => {
    localStorage.removeItem("authUser");
    localStorage.removeItem("token");
    return {
        type: LOG_OUT
    };
}

export const RegisterRequest = ():AuthActionTypes => {
    return {
        type: REGISTER_REQUEST
    };
}

export const RegisterSuccess = ():AuthActionTypes => {
    return {
        type: REGISTER_SUCCESS
    };
}

export const RegisterFailure = (error: any):AuthActionTypes => {
    return {
        type: REGISTER_FAILURE,
        error: error
    };
}

export const LoadUser = (user: User) :AuthActionTypes => {
    setUpAxiosInterceptors();
    return {
        type: LOAD_USER,
        user: user
    }
}