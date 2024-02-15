import { Outlet } from 'react-router-dom';
import styled from 'styled-components';
import { useState } from 'react';
import MenuIcon from '@mui/icons-material/Menu';

import SideBar from '@/components/sidebar/Sidebar';

const Container = styled.div`
  display: flex;
`;

const Main = styled.main<{ $open: boolean }>`
  padding: 0 32px;
  ${({ $open }) => {
    if ($open) {
      return 'margin-left: calc(var(--sidebar) + var(--sidebar-margin));';
    }
    return 'margin: 0 72px;';
  }}
  margin-bottom: 48px;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const MenuButton = styled.button`
  position: fixed;
  top: 20px;
  left: 20px;
  z-index: 100;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;

  &:hover {
    background-color: var(--rp-grey-600);
  }
`;

const SidebarLayout = () => {
  const [sidebarOpen, setSidebarOpen] = useState(true);
  const toggleSidebar = () => {
    setSidebarOpen((prev) => !prev);
  };

  return (
    <Container>
      <MenuButton>
        <MenuIcon onClick={toggleSidebar} />
      </MenuButton>
      {sidebarOpen && <SideBar />}
      <Main $open={sidebarOpen}>
        <Outlet />
      </Main>
    </Container>
  );
};

export default SidebarLayout;
