import styled from 'styled-components';
import { useState } from 'react';
// import { debounce } from 'lodash';
import Input from '../components/common/Input';
import Button from '../components/common/Button';
import { LoginStore } from '@/store/LoginStore';
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
  width: 480px;
  min-height: 460px;
  display: flex;
  flex-direction: column;
  align-items: center;
`;
const Title = styled.div`
  margin: 30px;
  font-size: 2rem;
  color: #fafafa;
`;

const SignUpMsg = styled.div`
  box-sizing: border-box;
  list-style-type: none;
  width: 100%;
  color: #919191;
  display: flex;
  justify-content: right;
  font-size: 0.6rem;
`;
const SignUp = styled(SignUpMsg)`
  width: 76%;
  display: flex;
  justify-content: flex-end;
  &:hover {
    cursor: pointer;
  }
`;

const Login = () => {
  const [id, setId] = useState('');
  const [pw, setPw] = useState('');
  const [isIdValid, setIsIdValid] = useState(false);
  const [isPwValid, setIsPwValid] = useState(false);
  const [idValidationMsg, setIdValidationMsg] = useState('');
  const [pwValidationMsg, setPwValidationMsg] = useState('');

  const login = LoginStore((state)=>state.login);
  const isLoggedIn = LoginStore((state)=>state.login);

  const onChangeId = (e: { target: { value: string } }) => {
    const input = e.target.value;
    setId(input);
    setIsIdValid(input.length >= 9 && input.length <= 16);
    setIdValidationMsg(idValidationMsg ? '' : 'Invalid Id');
  };

  const onChangePw = (e: { target: { value: string } }) => {
    const input = e.target.value;
    setPw(input);
    const pwRegex =
      /^(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?~\\-]).*(?=.*[a-zA-Z]).*(?=.*\d).{9,16}$/;
    setIsPwValid(pwRegex.test(input));
    setPwValidationMsg(pwValidationMsg ? '' : 'Invalid Pw');
  };

  const onClickLogin = async() => {
    const LoginData = {
      memberLoginId: id,
      memberPassword: pw,
    };
    console.log(`${LoginData} try to login..`)
    const success = await login(LoginData);
    console.log(`[Login Status(before)]:${isLoggedIn}`);
    console.log(`[Success?]:${success}`);

    console.log(`[Login Status(after)]:${isLoggedIn}`);
  };
  return (
    <>
      <Overlay>
        <FormWrapper>
          <Wrapper>
            <Title>Login</Title>
            <Input
              name='login'
              action='login'
              inputtype='normal'
              onChange={onChangeId}
            />
            <Input
              name='password'
              action='login'
              inputtype='password'
              onChange={onChangePw}
            />
            <SignUp>Forgot Password?</SignUp>
            <Button
              btntype='submit'
              buttonText='LOGIN'
              onClick={onClickLogin}
            />
            <Button
              btntype='google'
              buttonText='Google Login'
              onClick={onClickLogin}
            />
            <SignUp>Sign up</SignUp>
          </Wrapper>
        </FormWrapper>
      </Overlay>
    </>
  );
};

export default Login;
