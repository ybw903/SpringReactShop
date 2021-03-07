
export const login = async (username: string, password: string): Promise<void> => {

    const requestOption = {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({username, password})
    }

    const response = await fetch(`/authenticate`,requestOption);

    const response_data = await response.json();

    if(response.ok) {
        console.log("okok");
        console.log(response_data);
    }
}