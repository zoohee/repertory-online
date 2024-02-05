import styled from 'styled-components';
import Input from '@/components/common/Input';
import Button from '@/components/common/Button';
import { useState, useEffect } from 'react';
import { debounce } from 'lodash';

interface signUpData {
  id: string;
  pw: string;
  confirmPw: string;
  dancerName: string;
  email: string;
}
const FormWrapper = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  transform: translate(-50%, -50%);
  border-radius: 8px;
  z-index: 1000;
  /* width: 400px; */
  min-height: 300px;
  align-items: center;
  justify-content: center;
`;
const Overlay = styled.div`
  position: fixed;
  width: 100%;
  height: 100%;
  background-color: transparent;
  /* background: rgba(0, 0, 0, 0.5); */
  z-index: 999;
`;
const Wrapper = styled.div`
  box-sizing: border-box;
  border-radius: 8px;
  background-color: rgba(23, 23, 23, 0.8);
  padding: 40px 40px 40px 40px;
  width: 480px;
  min-height: 400px;
  display: flex;
  flex-direction: column;
  align-items: center;
  box-sizing: border-box;
`;
const Title = styled.div`
  margin: 30px;
  font-size: 2rem;
  color: #fafafa;
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
  const [pwMsg, setPwMsg] = useState('');
  const [nameMsg, setNameMsg] = useState('');
  const [emailMsg, setEmailMsg] = useState('');

  const [isIdValid, setIsIdValid] = useState(false);
  const [isPwValid, setIsPwValid] = useState(false);
  const [isNameValid, setIsNameValid] = useState(false);
  const [isEmailValid, setIsEmailValid] = useState(false);

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
    <>
      <Overlay>
        <FormWrapper>
          <Wrapper>
            <Title>SignUp</Title>
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
        </FormWrapper>
      </Overlay>
    </>
  );
};
export default SignUp;
