import React from 'react';
import { ListGroup } from 'react-bootstrap';

const OrderList = () => {
    return(
        <ListGroup>
        { [].map((product, i) =>(
            <ListGroup.Item key={i}>
                {/* {product.productName}
                
                <span className="cart-price">${product.productPrice} <span className="text-muted">{product.orderQuantity}개</span>
                {isEdit? <span className ="fa fa-times text-danger remove-item" onClick={()=> onRemoveFromCart(i)}></span>:<></>}
                </span> */}
                
            </ListGroup.Item>
        ))}
        
    </ListGroup>
    )
}

export default OrderList;