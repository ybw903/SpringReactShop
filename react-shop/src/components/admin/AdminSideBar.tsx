import React from "react";
import { Nav } from "react-bootstrap";
import { PropsFromToggle } from "react-bootstrap/esm/DropdownToggle";

type Props = {
    selectedPage: number,
    changePage: any
}

const AdminSideBar = ({selectedPage , changePage}: Props) => {
    return(
        <>
            <Nav className ="col-md-12 none d-md-block bg-light sidebar"
                >
                <Nav.Item onClick={()=>changePage(0)}>
                    <Nav.Link>회원정보</Nav.Link>
                </Nav.Item>
                <Nav.Item onClick={()=>changePage(1)}>
                    <Nav.Link>상품정보</Nav.Link>
                </Nav.Item>
                <Nav.Item onClick={()=>changePage(2)}>
                    <Nav.Link>주문정보</Nav.Link>
                </Nav.Item> 
            </Nav>
        </>
    );
}

export default AdminSideBar