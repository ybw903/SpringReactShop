import React, { useState } from 'react';
import { Button, Form } from 'react-bootstrap';
import { connect, ConnectedProps } from 'react-redux';
import { memberUpdate } from '../../service/auth';
import { RootState } from '../../store';
import { User } from '../../types/user';

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

    
    const handleSubmit = (e:React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const user:User = {username, phone, zipcode, street};
        memberUpdate(user).then(res=>console.log(res));
    };

    return (
        <Form onSubmit= {handleSubmit}>
            <h3 className="form-title">사용자 정보</h3>
            <div className="form-group">
                <label>Username</label>
                <input type="text" className="form-control" placeholder="이름을 입력하세요"
                defaultValue={username} name="username" readOnly/>
            </div>

            <div className="form-group">
                <label>우편번호</label>
                <input type="text" className="form-control" placeholder="123456" 
                defaultValue={zipcode} name="zipcode" onChange={e=>setZipcode(e.target.value)}/>
            </div>
            <div className="form-group">
                <label>주소</label>
                <input type="text" className="form-control" placeholder="서울시 강남구 테헤란로" 
                defaultValue={street} name="street" onChange={e=>setStreet(e.target.value)}/>
            </div>
            <div className="form-group">
                <label>전화번호</label>
                <input type="text" className="form-control" placeholder="010-1234-5678" 
                defaultValue ={phone} name="phone" onChange={e=>setPhone(e.target.value)}/>
            </div>
            <Button type="submit" className="btn btn-primary btn-lg btn-block">회원 정보 변경</Button>
    </Form>
    )
}
export default connector(UserInfo);