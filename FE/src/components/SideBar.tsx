import styled from 'styled-components';
import { Link } from 'react-router-dom';

import ProfileImage from '@/components/Image';
import Navigation from '@/components/SidebarNavigator';

const Aside = styled.aside`
  background-color: var(--rp-black);
  width: 240px;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Logout = styled.button`
  color: var(--rp-grey-500);
  background-color: transparent;
  border: 0;

  &:hover {
    color: var(--rp-yellow);
  }
`;

const SideBar = () => {
  return (
    <Aside>
      <Link to="/" style={{ margin: '48px' }}>
        <img src="images/logo.svg" alt="logo" style={{ height: '52px' }} />
      </Link>
      <ProfileImage size={176} isRound={true} src="images/index.jpg" />
      <div style={{ margin: '16px' }}>loginUserName</div>
      <Logout>Log Out</Logout>
      <Navigation/>
    </Aside>
  );
};

export default SideBar;
