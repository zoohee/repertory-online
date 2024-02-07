import styled from 'styled-components';
import { useState } from 'react';
// import { debounce } from 'lodash';
import { useDispatch } from 'react-redux';
import Input from '@/components/common/Input';
import Button from '@/components/common/Button';
import Overlay from '@/components/Overlay';
import { login } from '@/Redux/authentication';

const Wrapper = styled.div`
  padding: 48px;
  box-sizing: border-box;
  border-radius: 8px;
  background-color: rgba(23, 23, 23, 0.8);
  width: 480px;
  min-height: 460px;
  display: flex;
  flex-direction: column;
  align-items: center;
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
  const dispatch = useDispatch();

  // tmp
  console.log(isIdValid);
  console.log(isPwValid);

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
      /^(?=.*[!@#$%^&*()_+{}\]:;<>,.?~\\-]).*(?=.*[a-zA-Z]).*(?=.*\d).{9,16}$/;
    setIsPwValid(pwRegex.test(input));
    setPwValidationMsg(pwValidationMsg ? '' : 'Invalid Pw');
  };

  const onClickLogin = () => {
    console.log(id);
    console.log(pw);
    const LoginData = {
      memberLoginId: id,
      memberLoginPassword: pw,
    };

    dispatch(login(LoginData));
  };
  return (
    <Overlay>
      <Wrapper>
        <Input
          name='login'
          action='login'
          inputtype='ID'
          onChange={onChangeId}
        />
        <Input
          name='password'
          action='login'
          inputtype='password'
          onChange={onChangePw}
        />
        <SignUp>Forgot Password?</SignUp>
        <Button btntype='submit' buttonText='LOGIN' onClick={onClickLogin} />
        <Button
          btntype='google'
          buttonText='Google Login'
          onClick={onClickLogin}
        />
        <SignUp>Sign up</SignUp>
      </Wrapper>
    </Overlay>
  );
};

export default Login;
