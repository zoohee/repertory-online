import styled from 'styled-components';
import Input from '@/components/common/Input';
import Button from '@/components/common/Button';
import Overlay from '@/components/Overlay';
import { useState, useEffect } from 'react';
import { debounce } from 'lodash';

// interface signUpData {
//   id: string;
//   pw: string;
//   confirmPw: string;
//   dancerName: string;
//   email: string;
// }

const Wrapper = styled.div`
  box-sizing: border-box;
  border-radius: 8px;
  background-color: rgba(23, 23, 23, 0.8);
  padding: 48px;
  width: 480px;
  min-height: 400px;
  display: flex;
  flex-direction: column;
  align-items: center;
  box-sizing: border-box;
`;

const Msg = styled.div`
  width: 80%;
  margin-top: -12px;
  margin-left: 24px;
  font-size: 0.6rem;
  height: 1rem;
`;
const SignUp = () => {
  const [signUpData, setSignUpData] = useState({
    id: '',
    pw: '',
    confirmPw: '',
    dancerName: '',
    email: '',
  });

  const [idMsg, setIdMsg] = useState('');
  const [pwMsg] = useState('');
  const [nameMsg] = useState('');
  const [emailMsg] = useState('');

  const [isIdValid, setIsIdValid] = useState(false);
  const [isPwValid] = useState(false);
  const [isNameValid] = useState(false);
  const [isEmailValid] = useState(false);

  // const [pwMsg, setPwMsg] = useState('');
  // const [nameMsg, setNameMsg] = useState('');
  // const [emailMsg, setEmailMsg] = useState('');

  // const [isIdValid, setIsIdValid] = useState(false);
  // const [isPwValid, setIsPwValid] = useState(false);
  // const [isNameValid, setIsNameValid] = useState(false);
  // const [isEmailValid, setIsEmailValid] = useState(false);

  useEffect(() => {
    // console.log(idMsg);
  }, [signUpData]);

  const onClickSignUp = () => {
    if (isIdValid && isPwValid && isNameValid && isEmailValid) {
      console.log('회원가입 성공');
    } else {
      console.log('회원가입 실패');
    }
  };

  const onChangeInput = (e: { target: { name: string; value: string } }) => {
    const { name, value } = e.target;
    setSignUpData({
      ...signUpData,
      [name]: value,
    });
    checkId(e.target.value);
  };

  const checkId = debounce((value) => {
    const isValid = value.length >= 9 && value.length <= 16;
    setIsIdValid(isValid);
    setIdMsg(isValid ? '' : '아이디 형식이 올바르지 않습니다(9-16자리)');
  }, 500);

  return (
    <Overlay>
      <Wrapper>
        <Input name='ID' action='signup' onChange={onChangeInput} />
        <Msg>{idMsg}</Msg>
        <Input
          name='PASSWORD'
          action='signup'
          inputtype='password'
          onChange={onChangeInput}
        />
        <Msg>{pwMsg}</Msg>
        <Input
          name='CONFIRM PASSWORD'
          action='signup'
          inputtype='password'
          onChange={onChangeInput}
        />
        <Msg>{pwMsg}</Msg>
        <Input name='DANCER NAME' onChange={onChangeInput} action={''} />
        <Msg>{nameMsg}</Msg>
        <Input name='EMAIL' onChange={onChangeInput} action={''} />
        <Msg>{emailMsg}</Msg>
        <Button
          btntype='submit'
          onClick={onClickSignUp}
          buttonText={'Sign up'}
        />
      </Wrapper>
    </Overlay>
  );
};
export default SignUp;
