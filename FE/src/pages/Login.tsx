import styled from 'styled-components';
import { useState, useEffect } from 'react';
// import { debounce } from 'lodash';
import Input from '@/components/common/Input';
import Button from '@/components/common/Button';
import Overlay from '@/components/Overlay';
import { LoginStore } from '@/store/LoginStore';
import { useNavigate } from 'react-router';
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
interface ILoginData {
  memberLoginId: string;
  memberPassword: string;
}
type LoginFunction = (data: ILoginData) => Promise<boolean>;

const Login = () => {
  const isLoggedIn = LoginStore((state: { isLoggedin: boolean; }) => state.isLoggedin);
  useEffect(() => {
    if (isLoggedIn) BlockAccess();
  }, []);
  const [id, setId] = useState('');
  const [pw, setPw] = useState('');
  const [
    // isIdValid
    , setIsIdValid] = useState(false);
  const [
    // sPwValid
    , setIsPwValid] = useState(false);
  const [idValidationMsg, setIdValidationMsg] = useState('');
  const [pwValidationMsg, setPwValidationMsg] = useState('');

  const memberLogin = LoginStore((state) => state.login as LoginFunction);
  const navigate = useNavigate();
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
      /^(?=.*[!@#$%^&*()_+{}[\]:;<>,.?~\\-]).*(?=.*[a-zA-Z]).*(?=.*\d).{9,16}$/;
    setIsPwValid(pwRegex.test(input));
    setPwValidationMsg(pwValidationMsg ? '' : 'Invalid Pw');
  };

  const onClickLogin = async () => {
    const LoginData = {
      memberLoginId: id,
      memberPassword: pw,
    };
    console.log(`${LoginData} try to login..`);
    const success = await memberLogin(LoginData);
    console.log(`[Login Status(before)]:${isLoggedIn}`);
    console.log(`[Success?]:${success}`);
    console.log(`[Login Status(after)]:${isLoggedIn}`);

    if (success) {
      navigate('/');
    }
  };

  const BlockAccess = () => {
    navigate('/');
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
