import styled from 'styled-components';

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

const Label = styled.div`
  font-size: 0.75rem;
  color: #bebebe;
  display: flex;
  width: 80%;
  margin: 6px;
`;
const InputForm = styled.input.attrs<InputFormProps>((props) => ({
  type: props.inputtype === 'PASSWORD' ? 'password' : '',
  placeholder:
    props.action === 'login'
      ? `Enter Your ${props.inputtype}`
      : `${props.inputtype}`,
}))<InputFormProps>`
  color: white;
  background-color: #0d0d0d;
  box-sizing: border-box;
  width: 80%;
  height: 30px;
  border-radius: 26px;
  border: none;
  margin-bottom: 20px;
  padding: 20px;
  font-size: 0.75rem;
  font-family: 'Pretendard-Regular', sans-serif;
`;

const Input: React.FC<InputProps> = ({ action, name, inputtype, onChange }) => {
  return (
    <>
      {action == 'login' && <Label>{inputtype}</Label>}
      <InputForm name={name} inputtype={inputtype} onChange={onChange} />
    </>
  );
};
export default Input;
