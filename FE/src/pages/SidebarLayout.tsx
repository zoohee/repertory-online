import { Outlet } from 'react-router-dom';
import styled from 'styled-components';

import SideBar from '@/components/sidebar/Sidebar';

const Container = styled.div`
  display: flex;
`;

const Main = styled.main`
  padding: 0 32px;
  margin-left: calc(var(--sidebar) + var(--sidebar-margin) * 2);
  margin-bottom: 48px;
  width: 100%;
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
