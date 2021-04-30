import React, { useEffect, useState } from "react";
import { Button, ListGroup } from "react-bootstrap";
import { getMembers } from "../../service/admin/member";
import { User } from "../../types/user";

const AdminMembers = () => {
    const [memberList, setMemberList] = useState([] as User[]);

    useEffect(() => {
        getMembers(0,'')
            .then(membersPage => {
                setMemberList(membersPage.members);
        });
    }, [])


    return(
        <ListGroup >
            <ListGroup.Item className ="d-flex justify-content-between"><span>회원이름</span> <span>회원번호</span> <span>회원주소</span> <span>회원우편번호</span></ListGroup.Item>
            {  
                memberList.map((member) => {
                    return <ListGroup.Item className ="d-flex justify-content-between">
                        <span>{member.username}</span> 
                        <span>{member.phone}</span>
                        <span>{member.street}</span>
                        <span>{member.zipcode}</span>
                    </ListGroup.Item>
                })
            }
        </ListGroup>
        
    );

}
export default AdminMembers;
