import styled from 'styled-components';
import { Link } from 'react-router-dom';

import ProfileImage from '@/components/ProfileImage';

const Bar = styled.div`
  height: 100%;
  background-color: var(--rp-black);
  color: var(--rp-white);
  width: 240px;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Logout = styled.button`
  color: var(--rp-grey-500);
  height: 1rem;
  background-color: transparent;
  border: 0;

  &:hover {
    color: var(--rp-yellow);
  }
`;

const Nav = styled.ul`
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  margin-top: 64px;
`;

const NavButton = styled.li`
  margin: 16px;
  grid-column: 6 span;

  a {
    color: var(--rp-whtie);
    text-decoration-line: none;
  }

  a:hover {
    color: var(--rp-orange);
  }
`;

const SideBar = () => {
  return (
    <Bar>
      <Link to="/" style={{ margin: '48px' }}>
        <img src="images/logo.svg" alt="logo" style={{ height: '48px' }} />
      </Link>
      <ProfileImage size={176} src="images/index.jpg" />
      <div style={{ margin: '16px' }}>loginUserName</div>
      <Logout>Log Out</Logout>
      <Nav>
        <NavButton>Workspace</NavButton>
        <NavButton>
          <Link to="/community">Community</Link>
        </NavButton>
        <NavButton>My page</NavButton>
      </Nav>
    </Bar>
  );
};

export default SideBar;
