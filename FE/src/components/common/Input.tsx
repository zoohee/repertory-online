import { useState } from 'react';
import styled from 'styled-components';
import visibility from '/images/visibility.svg';
import visibilityOff from '/images/visibilityOff.svg';

interface InputProps {
  action: string;
  name: string;
  inputtype?: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

interface InputFormProps {
  inputtype?: string;
  action?: string;
}
const VisibilityIcon = styled.img`
  position: fixed;
  margin-top: 0.6rem;
  margin-left: 18rem;
  width: 1.2rem;
  height: 1.2rem;
  width: auto;
  border-radius: 0.6rem;
  &:hover {
    cursor: pointer;
  }
`;
const Label = styled.div`
  font-size: 0.75rem;
  color: #bebebe;
  display: flex;
  width: 80%;
  margin: 6px;
`;
const InputWrapper = styled.div`
  display: flex;
  width: 80%;
`;
const InputForm = styled.input.attrs<InputFormProps>((props) => ({
  type: props.inputtype === 'password' ? 'password' : '',
  placeholder:
    props.action === 'login' ? `Enter Your ${props.name}` : `${props.name}`,
}))<InputFormProps>`
  color: white;
  background-color: #0d0d0d;
  box-sizing: border-box;
  width: 100%;
  height: 30px;
  border-radius: 26px;
  border: none;
  margin-bottom: 20px;
  padding: 20px;
  font-size: 0.75rem;
`;

const Input: React.FC<InputProps> = ({ action, name, inputtype, onChange }) => {
  const [isVisible, setIsVisible] = useState(false);
  return (
    <>
      {action == 'login' && <Label>{inputtype}</Label>}
      <InputWrapper>
        <InputForm
          name={name}
          inputtype={inputtype === 'password' && !isVisible ? 'password' : ''}
          onChange={onChange}
        />
        {inputtype === 'password' && action === 'signup' && (
          <VisibilityIcon
            src={isVisible ? visibility : visibilityOff}
            onClick={() => setIsVisible(!isVisible)}
          />
        )}
      </InputWrapper>
    </>
  );
};
export default Input;
