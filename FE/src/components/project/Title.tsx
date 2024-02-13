import styled from "styled-components";


const TitleWrapper = styled.div`
    height : 5rem;
    width : 100%;
    padding : 1.5rem;
    font-size : 1.8rem;
    color : white;
    text-align:left;
    border-bottom: 0.6rem solid var(--background-color);
`
export const Title = ({title})=>{
    return(
        <TitleWrapper>
            {title}
        </TitleWrapper>
    )
}