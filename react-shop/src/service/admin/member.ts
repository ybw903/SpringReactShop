import { Page } from "../../types/page";


export const getMembers = async (page:number, sortedBy:string) => {

    const token = localStorage.getItem('token');
    const requestOption = {
        headers : {'Authorization' : 'Bearer ' + token}
    };

    const response = await fetch(`/api/members?page=${page}&size=16&sort=${sortedBy},desc`, requestOption);
    const response_data = await response.json();
    console.log(response_data);
    const members = await response_data._embedded.memberResources;
    const pages: Page = await response_data.page;


    return { members, pages};
}