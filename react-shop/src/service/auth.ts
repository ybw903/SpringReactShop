import { User } from "../types/user";
import axios from 'axios';

export const authLogin =  (username: string, password: string) => {
    return axios.post("/api/authenticate/login",{username, password})
}

export const authSignUp = (username: string, password: string) => {
    return axios.post("/api/authenticate/signup", {username, password})
}