import styled from 'styled-components';
import { useNavigate, Outlet } from 'react-router-dom';

import SearchBar from '@/components/SearchBar';
import URL from '@/url';
import SearchFilter from '@/components/feed/SearchFilter';

const FixedBox = styled.div`
  z-index: 10;
  padding: var(--searchbar-community-padding) 0;
  width: calc(100vw - var(--sidebar) - var(--sidebar-margin));
  height: calc(
    var(--searchbar-height) + var(--searchbar-community-padding) * 2
  );
  display: flex;
  justify-content: center;
  position: fixed;
  background-color: var(--background-color);
`;

const Container = styled.div`
  width: 100%;
  max-width: calc(100vw - var(--sidebar) - var(--sidebar-margin));
  margin-top: calc(
    var(--searchbar-height) + var(--searchbar-community-padding) * 2
  );
  padding: 16px 0;
`;

const CommunityLayout = () => {
  const navigate = useNavigate();
  const search = (keyword: string) => {
    navigate(`${URL.communitySearch}/${keyword}`);
  };

  return (
    <>
      <FixedBox>
        <SearchFilter />
        <SearchBar search={search} />
      </FixedBox>
      <Container>
        <Outlet />
      </Container>
    </>
  );
};

export default CommunityLayout;
