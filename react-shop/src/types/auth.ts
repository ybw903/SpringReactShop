import { User } from "./user";

export const LOGIN_REQUEST = 'LOGIN_REQUEST';
export const LOGIN_SUCCESS = 'LOGIN_SUCCESS';
export const LOGIN_FAILURE = 'LOGIN_FAILURE';
export const LOG_OUT = 'LOG_OUT';

export const REGISTER_REQUEST = 'REGISTER_REQUEST';
export const REGISTER_SUCCESS = 'REGISTER_SUCCESS';
export const REGISTER_FAILURE = 'REGISTER_FAILURE';

export const LOAD_USER = 'LOAD_USER';

export interface LoginRequest{
    type : typeof LOGIN_REQUEST
}

export interface LoginSuccess{
    type : typeof LOGIN_SUCCESS
    user: User
}

export interface LoginFauilure {
    type : typeof LOGIN_FAILURE
    error: any
}

export interface LogOut {
    type : typeof LOG_OUT
}

export interface RegisterRequest{
    type : typeof REGISTER_REQUEST
}

export interface RegisterSuccess{
    type : typeof REGISTER_SUCCESS
}

export interface RegisterFailure{
    type : typeof REGISTER_FAILURE
    error : any
}

export interface loadUser{
    type : typeof LOAD_USER
    user: User
}

export type AuthActionTypes = LoginRequest|LoginSuccess|LoginFauilure|LogOut|RegisterRequest|RegisterSuccess|RegisterFailure|loadUser;

export type AuthState = {
    loggedIn?: boolean;
    isFetching?: boolean;
    user?: User;
    error?: any;
    isAdmin? : boolean;
}
