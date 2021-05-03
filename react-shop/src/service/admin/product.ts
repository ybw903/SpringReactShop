import axios from "axios";
import { Product } from "../../types/products";

export const addProduct = async(product : any) => {
    return axios.post("/api/products",product);
}