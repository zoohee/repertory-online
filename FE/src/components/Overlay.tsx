import styled from 'styled-components';
import { Link } from 'react-router-dom';

const Container = styled.div`
  position: fixed;
  width: 100%;
  height: 100%;
  background-color: transparent;
  background-image: url(/images/index.jpg);
  background-size: cover;
  background-position: center;
  z-index: 999;
`;

const Wrapper = styled.div`
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

const Home = styled(Link)`
  margin-bottom: 16px;
`;

interface Props {
  children?: React.ReactNode;
}

const Overlay = ({ children }: Props) => {
  return (
    <Container>
      <Wrapper>
        <Home to="/">
          <img src="/images/logo.svg" alt="logo" />
        </Home>
        {children}
      </Wrapper>
    </Container>
  );
};

export default Overlay;