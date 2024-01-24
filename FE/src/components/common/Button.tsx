import React from "react";
import styled,{css} from "styled-components";
import Symbol from "../../images/GoogleSymbol.png"
const buttonStyles = {
    default: css`
        background-color: #0D0D0D;
        color : white;
    `,
    submit: css`
        color : #0D0D0D;
        background-color: #FEE800;
        &:hover{
            cursor: pointer;
            background-color: #c2b200;
        }
    `,
    google: css`
        color : #0D0D0D;
        background-color: #F0F0F0;
        &:hover{
            cursor: pointer;
            background-color: #a8a8a8;
        }
    `
};
const ButtonIcon = styled.img`
    margin-right: 8px;
    height:1.5rem;
    width : auto;
`
const StyledButton = styled.button`
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #0D0D0D;
    color : white;
    border: none;
    width : 368px;
    height : 3rem;
    border-radius: 1.5rem;
    margin : 12px 0 12px 0;
    font-size : 1rem;
    ${props => buttonStyles[props.btntype || 'default']}
`
const Button= ({btntype,buttonText,onClickEvent,})=> {
    return(
        <StyledButton
            btntype={btntype}
            onClick={onClickEvent}>
            {btntype==="google" && <ButtonIcon src={Symbol}alt="Google-Symbol"/>}
            {buttonText}
        </StyledButton>
    )
}
export default Button;