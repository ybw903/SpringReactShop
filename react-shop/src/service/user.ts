export const loadUser = () => {
    try{
       const username = localStorage.getItem('authUser');
       if(!username)return; 
       getUserProfile(username);
    } catch {
        console.log('localStorage is not working');
    }
}

export const getUserProfile = async (username: string) => {
    const response = await fetch(`/api/users/${username}`);

    const response_data = await response.json();
    
    return;
}