import { Outlet } from 'react-router-dom';
import styled from 'styled-components';

import SideBar from '@/components/sidebar/Sidebar';

const Container = styled.div`
  display: flex;
  background-color: var(--rp-grey-800);
`;

const Main = styled.main`
  padding: 0 32px;
  min-width: calc(100% - 240px);
  display: flex;
  flex-direction: column;
`;

const SidebarLayout = () => {
  return (
    <Container>
      <SideBar />
      <Main>
        <Outlet />
      </Main>
    </Container>
  );
};

export default SidebarLayout;
