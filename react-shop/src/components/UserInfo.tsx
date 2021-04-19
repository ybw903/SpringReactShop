import React, { useState } from 'react';
import { Button, Form } from 'react-bootstrap';
import { connect, ConnectedProps } from 'react-redux';
import { RootState } from '../store';

const mapState = ({authState}: RootState) =>({
    user : authState.user,
});

const mapDispatch = {
   
}

const connector = connect(
    mapState,
    mapDispatch,
);

type PropsFromRedux = ConnectedProps<typeof connector>

type Props = PropsFromRedux& {
}

const UserInfo = ({user}:Props) => {
    
    const username = user?.username?user?.username:'';
    const [phone, setPhone] = useState(user?.phone?user?.phone:'');
    const [zipcode, setZipcode] = useState(user?.zipcode?user?.zipcode:'');
    const [street, setStreet] = useState(user?.street?user?.street:'');

    return (
        <Form>
            <h3 className="form-title">사용자 정보</h3>
            <div className="form-group">
                <label>Username</label>
                <input type="text" className="form-control" placeholder="이름을 입력하세요"
                defaultValue={username} name="username" readOnly/>
            </div>

            <div className="form-group">
                <label>우편번호</label>
                <input type="text" className="form-control" placeholder="123456" 
                defaultValue={zipcode} name="zipcode"/>
            </div>
            <div className="form-group">
                <label>주소</label>
                <input type="text" className="form-control" placeholder="서울시 강남구 테헤란로" 
                defaultValue={street} name="street"/>
            </div>
            <div className="form-group">
                <label>전화번호</label>
                <input type="password" className="form-control" placeholder="010-1234-5678" 
                defaultValue ={phone} name="phone"/>
            </div>
            <Button type="submit" className="btn btn-primary btn-lg btn-block">회원 정보 변경</Button>
    </Form>
    )
}
export default connector(UserInfo);