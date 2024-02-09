import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { useEffect } from 'react';
import { fontSize } from '@/styles/font';
import { LoginStore } from '@/store/LoginStore';

const GridContainer = styled.div`
  background-color: var(--rp-black);
  padding-bottom: 10rem;
  padding-top: 5rem;
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  grid-template-rows: 4.5rem 1fr 4.5rem 4.5rem;
  justify-content: center;
  height: 100%;
`;

const GridBox = styled.div<{ column: number; row: number }>`
  margin-right: 32px;
  grid-column: ${(props) => props.column};
  grid-row: ${(props) => props.row};
  display: flex;
  padding-bottom: 3rem;

  a {
    font-family: 'YdestreetB';
    ${fontSize.l}
    text-decoration-line: none;
  }

  a:hover {
    color: var(--rp-yellow);
  }
`;

const Logo = styled.img`
  grid-column: 2 / span 3;
  grid-row: 2;
  z-index: 1;
  max-width: 300px;
`;

const ImageBox = styled.div`
  grid-row: 2 / span 3;
  grid-column: 3 / span 4;
  height: calc(100vh - 10rem - 4.5rem);
  width: 100%;

  img {
    object-fit: cover;
    height: 100%;
    width: 100%;
  }
`;

export default function HomePage() {
  const isLoggedIn = LoginStore((state) => state.isLoggedin);
  const logout = LoginStore((state)=> state.logout);
  useEffect(() => {
    console.log(`[Login Status]: ${isLoggedIn ? 'Logged in ' : 'Logged Out'}`);
  }, [isLoggedIn]);
  return (
    <GridContainer>
      <Logo src="images/logo.svg" alt="logo" />
      <ImageBox>
        <img src="images/index.jpg" alt="image" />
      </ImageBox>
      {isLoggedIn ? (<GridBox column={4} row={1}>
        <div onClick={logout}>Log Out</div>
      </GridBox>) : (<GridBox column={4} row={1}>
        <Link to="/login">Log In</Link>
      </GridBox>)}


      <GridBox column={5} row={1}>
        <Link to="/signup">Sign Up</Link>
      </GridBox>
      <GridBox column={2} row={3}>
        <Link to="/projects">Create</Link>
      </GridBox>
      <GridBox column={2} row={4}>
        <Link to="/community">Community</Link>
      </GridBox>
    </GridContainer>
  );
}
