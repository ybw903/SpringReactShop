import React, { useState } from 'react';
import { Button, Modal } from 'react-bootstrap';
import { addProduct } from '../../service/admin/product';

interface props {
    showModal: boolean,
    handleClose: ()=>void,
    form: any,
    setForm: any,
    formDescription: any,
    handleSubmit: any
}
  

const FormModal = ({showModal,  handleClose, form, setForm, formDescription, handleSubmit}: props) => {


    const {val1, val2, val3, val4} = form;
    const {formTitle, formLabel1, formLabel2, formLabel3, formLabel4} = formDescription;

    const handleChange = (e:React.ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;
        setForm({
            ...form,
            [name]:value
        });
        console.log(form);
    };

    return (
        <Modal show={showModal} onHide={handleClose}>
            <Modal.Header closeButton>
                    {formTitle}
            </Modal.Header>
            <Modal.Body>

                <div className="form-group">
                    <label>{formLabel1}</label>
                    <input className="form-control" type="text" name="productName" value={val1} onChange={handleChange}></input>
                </div>
                <div className="form-group">
                    <label>{formLabel2}</label>
                    <input className="form-control" type="text" name="productDescription" value={val2} onChange={handleChange}></input>
                </div>
                <div className="form-group">
                    <label>{formLabel3}</label>
                    <input className="form-control" type="number" name="productPrice" value={val3} onChange={handleChange}></input>
                </div>
                <div className="form-group">
                    <label>{formLabel4}</label>
                    <input className="form-control" type="number" name="productQuantity" value={val4} onChange={handleChange}></input>
                </div>
            </Modal.Body>
            <Modal.Footer>
                <Button className="btn btn-primary btn-lg btn-block" onClick={() => handleSubmit()}>{formTitle}</Button>
            </Modal.Footer>
        </Modal>
    )
}

export default FormModal;