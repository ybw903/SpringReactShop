import React from 'react';
import { Button, Form } from 'react-bootstrap';

const UserInfo = () => {
    return (
        <Form>
            <h3 className="form-title">사용자 정보</h3>
            <div className="form-group">
                <label>Username</label>
                <input type="text" className="form-control" placeholder="이름을 입력하세요"
                name="username"/>
            </div>

            <div className="form-group">
                <label>Password</label>
                <input type="password" className="form-control" placeholder="비밀번호를 입력하세요" 
                name="password"/>
            </div>
            <div className="form-group">
                <label>우편번호</label>
                <input type="text" className="form-control" placeholder="123456" 
                name="zipcode"/>
            </div>
            <div className="form-group">
                <label>주소</label>
                <input type="text" className="form-control" placeholder="서울시 강남구 테헤란로" 
                name="street"/>
            </div>
            <div className="form-group">
                <label>전화번호</label>
                <input type="password" className="form-control" placeholder="010-1234-5678" 
                name="phone"/>
            </div>
            <Button type="submit" className="btn btn-primary btn-lg btn-block">회원 정보 변경</Button>
    </Form>
    )
}

export default UserInfo;