import React from 'react';
import {
    Card,
    Button
} from 'react-bootstrap';

const ProductCard = () => {
    return(
        <Card>
            <Card.Body>
                <Card.Title>카드의 제목</Card.Title>
                <Card.Text>카드의 내용</Card.Text>
                <span>카드의 가격</span>
                <Button className="cart-button" variant="outline-primary">카트에 담기</Button>
            </Card.Body>
        </Card>
    )
}

export default ProductCard;