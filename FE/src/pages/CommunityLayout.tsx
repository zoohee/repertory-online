import styled from 'styled-components';
import { Outlet } from 'react-router';

import SearchBar from '@/components/SearchBar';

const FixedBox = styled.div`
  z-index: 10;
  padding: var(--searchbar-community-padding) 0;
  width: calc(100vw - var(--sidebar) - var(--sidebar-margin) * 2);
  display: flex;
  justify-content: center;
  position: fixed;
  left: calc(var(--sidebar) + var(--sidebar-margin) * 2);
  background-color: var(--background-color);
`;

const Container = styled.div`
  width: 100%;
  margin-top: calc(
    var(--searchbar-height) + var(--searchbar-community-padding) * 2
  );
  padding: 16px 0;
`;

const CommunityLayout = () => {
  return (
    <>
      <FixedBox>
        <SearchBar />
      </FixedBox>
      <Container>
        <Outlet />
      </Container>
    </>
  );
};

export default CommunityLayout;
