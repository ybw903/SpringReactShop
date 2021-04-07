import { User } from "../types/user";
import axios from 'axios';
import { config } from "node:process";

export const authLogin =  (username: string, password: string) => {
    return axios.post("/api/authenticate/login",{username, password})
}

export const setUpAxiosInterceptors = () => {
    axios.interceptors.request.use(
        config => {
            const token = localStorage.getItem('token');
            if(token) {
                config.headers['Authorization'] = 'Bearer ' + token;
            }
            return config;
        },
        error => {
            Promise.reject(error);
        }
    );
}

export const authSignUp = (username: string, password: string) => {
    return axios.post("/api/authenticate/signup", {username, password})
}