import {LOGIN_REQUEST, LOGIN_SUCCESS, LOGIN_FAILURE, LOG_OUT, AuthActionTypes, REGISTER_REQUEST, REGISTER_SUCCESS, REGISTER_FAILURE} from '../types/auth';
import { User } from '../types/user';
import {History} from 'history';
import axios from 'axios';
import { authLogin, authSignUp } from '../service/auth';

export function login(username:string, password:string, history: History) {
    return (dispatch: (authAction: AuthActionTypes) => void) => {
        dispatch(loginRequest());

        authLogin(username, password)
        .then((response) =>{
            let token: string = response.data;
            console.log(token);
            dispatch(LoginSuccess({username, token}));
            history.push('/');
        }).catch((error)=>{
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
            console.log(error);
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