import { User } from "../types/user";

export const loadUser = () => {
    try{
       const username = localStorage.getItem('authUser');
       if(!username)return; 
       const user = getUserProfile(username);
       return user;
        
    } catch {
        console.log('localStorage is not working');
    }
}

export const getUserProfile = async (username: string) => {
    const token = localStorage.getItem('token');
    const requestOption = {
        headers : {'Authorization' : 'Bearer ' + token}
    };
    const response = await fetch(`/api/members/${username}`,requestOption);
    if(response.status===200) {
        const responseJson= await response.json();
        const user :User = {
            username : responseJson.username,
            phone : responseJson.phone,
            zipcode : responseJson.zipcode,
            street : responseJson.street,
        };
        return user;
    }
    return;
}