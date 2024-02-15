import styled from 'styled-components';
import { Link } from 'react-router-dom';

import ProfileImage from '@/components/common/ImageSquare';
import Navigation from '@/components/sidebar/Navigation';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 28px 0;
`;

const Aside = styled(Container)`
  z-index: 10;
  position: fixed;
  margin: var(--sidebar-margin);
  border-radius: 10px;
  min-height: calc(100vh - var(--sidebar-margin) * 2);
  background-color: var(--sidebar-color);
  width: var(--sidebar);
  overflow-y: auto;
  scrollbar-width: thin;
`;

const Logout = styled.button`
  font-size: var(--font-size-s);
  color: var(--rp-grey-500);
  border: 0;

  &:hover {
    color: var(--rp-yellow);
  }
`;

const SideBar = () => {
  return (
    <Aside as="aside">
      <Link to="/">
        <img src="/images/logo.svg" alt="logo" style={{ height: '52px' }} />
      </Link>
      <Container>
        <ProfileImage size={144} isRound={true} src="/images/index.jpg" />
        <div style={{ margin: '16px' }}>loginUserName</div>
        <Logout>Log Out</Logout>
      </Container>
      <Navigation />
    </Aside>
  );
};

export default SideBar;
