import React, { useState } from "react";
import { Col, Container, Row } from "react-bootstrap";
import AdminMembers from "./AdminMembers";
import AdminOrders from "./AdminOrder";
import AdminProducts from "./AdminProducts";
import AdminSideBar from "./AdminSideBar";


const Admin = () => {
    const [page,setPage] = useState(0);

    return(
        <>
        <Container fluid>
            <Row>
                <Col xs={2} id="sidebar-wrapper">
                    <AdminSideBar selectedPage= {page} changePage={setPage}/>
                </Col>
                <Col xs={10} id="page-content-wrapper">
                    {
                        (page===0)?
                        <AdminMembers/>:
                        (page===1)?
                        <AdminOrders/>:
                        <AdminProducts/>
                    }
                </Col>
            </Row>
        </Container>
        </>
    );
}

export default Admin;