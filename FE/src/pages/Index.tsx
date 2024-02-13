import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { useEffect } from 'react';
import { fontSize } from '@/styles/font';
import { LoginStore } from '@/store/LoginStore';
import URL from '@/url';

const GridContainer = styled.div`
  padding: var(--homepage-padding) 0;
  display: grid;
  grid-template-columns: 10% 80% 10%;
  grid-template-rows:
    var(--homepage-grid-row-size) calc(100% - var(--homepage-grid-row-size) * 2)
    var(--homepage-grid-row-size);
  height: 100%;
`;

const List = styled.ul`
  grid-column: 2;
  grid-row: 1;
  display: flex;
  justify-content: flex-end;
`;

const ListItem = styled.li`
  margin-left: 2rem;
  margin-bottom: 2rem;
`;

const StyledLink = styled(Link)`
  font-family: 'YdestreetB';
  ${fontSize.l}
  text-decoration-line: none;
  text-shadow: var(--text-shadow);

  &:hover {
    cursor: pointer;
    color: var(--rp-yellow);
  }
`;

const StyledDiv = styled.div`
  font-family: 'YdestreetB';
  ${fontSize.l}
  text-decoration-line: none;
  text-shadow: var(--text-shadow);

  &:hover {
    cursor: pointer;
    color: var(--rp-yellow);
  }
`;

const Box = styled.div`
  grid-column: 2 / span 2;
  grid-row: 2;
  display: grid;
  grid-template-columns: repeat(5, 20%);
`;

const InnerBox = styled.div`
  width: 100%;
  grid-column: 1 / span 2;
  grid-row: 1;
  z-index: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;

  img {
    margin-top: 16px;
    object-fit: contain;
    width: 100%;
    min-width: 300px;
  }
`;

const ImageBox = styled.div`
  grid-row: 1;
  grid-column: 2 / span 4;
  height: calc(
    100vh - var(--homepage-grid-row-size) * 2 - var(--homepage-padding) * 2
  );
  width: 100%;

  img {
    object-fit: cover;
    height: 100%;
    width: 100%;
  }
`;

export default function HomePage() {
  const isLoggedIn = LoginStore((state) => state.isLoggedin);
  const logout = LoginStore((state) => state.logout);
  useEffect(() => {
    console.log(`[Login Status]: ${isLoggedIn ? 'Logged in ' : 'Logged Out'}`);
  }, [isLoggedIn]);
  return (
    <GridContainer>
      <List>
        <ListItem>
          {isLoggedIn ? (
            <StyledDiv onClick={logout}>Logout</StyledDiv>
          ) : (
            <StyledLink to={URL.login}>Log In</StyledLink>
          )}
        </ListItem>
        <ListItem>
          <StyledLink to={URL.signUp}>Sign Up</StyledLink>
        </ListItem>
      </List>
      <Box>
        <InnerBox>
          <img src='images/logo.svg' alt='logo' />
          <ul>
            <ListItem>
              <StyledLink to={URL.projects}>Create</StyledLink>
            </ListItem>
            <ListItem>
              <StyledLink to={URL.community}>Community</StyledLink>
            </ListItem>
          </ul>
        </InnerBox>
        <ImageBox>
          <img src='images/index.jpg' alt='home' />
        </ImageBox>
      </Box>
    </GridContainer>
  );
}
