import styled, { css } from 'styled-components';
import Symbol from '/images/GoogleSymbol.png';
import { ButtonHTMLAttributes } from 'react';

export const buttonStyles = {
  default: css`
    background-color: var(--rp-yellow);
    color: var(--background-color);
    * {
      color: var(--background-color);
    }

    &:hover {
      background-color: var(--rp-orange);
      color: var(--rp-white);
      * {
        color: var(--rp-white);
      }
    }
  `,
  submit: css`
    color: #0d0d0d;
    background-color: #fee800;
    &:hover {
      cursor: pointer;
      background-color: #c2b200;
    }
  `,
  google: css`
    color: #0d0d0d;
    background-color: #f0f0f0;
    &:hover {
      cursor: pointer;
      background-color: #a8a8a8;
    }
  `,
};
const ButtonIcon = styled.img`
  margin-right: 8px;
  height: 1.5rem;
  width: auto;
`;

interface StyledButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  btntype?: 'default' | 'submit' | 'google';
}
interface ButtonProps extends StyledButtonProps {
  buttonText: string;
}
const StyledButton = styled.button<StyledButtonProps>`
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #0d0d0d;
  color: white;
  border: none;
  width: 368px;
  height: 3rem;
  border-radius: 1.5rem;
  margin: 12px 0 12px 0;
  font-size: 1rem;
  ${(props) => buttonStyles[props.btntype || 'default']}
`;
const Button: React.FC<ButtonProps> = ({
  btntype,
  buttonText,
  onClick,
  ...props
}) => {
  return (
    <StyledButton btntype={btntype} onClick={onClick} {...props}>
      {btntype === 'google' && <ButtonIcon src={Symbol} alt='Google-Symbol' />}
      {buttonText}
    </StyledButton>
  );
};
export default Button;
