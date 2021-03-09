import { User } from "./user";

export const LOGIN_REQUEST = 'LOGIN_REQUEST';
export const LOGIN_SUCCESS = 'LOGIN_SUCCESS';
export const LOGIN_FAILURE = 'LOGIN_FAILURE';

export interface LoginRequest{
    type : typeof LOGIN_REQUEST;
}

export interface LoginSuccess{
    type : typeof LOGIN_SUCCESS;
}

export interface LoginFauilure {
    type : typeof LOGIN_FAILURE;
}

export type AuthActionTypes = LoginRequest|LoginSuccess|LoginFauilure;

export type AuthState = {
    loggedIn?: boolean;
    loggingIn?: boolean;
    user?: User;
    error?: any;
}
