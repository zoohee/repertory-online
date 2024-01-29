import { Outlet } from 'react-router';

import SearchBar from '@/components/SearchBar';

const CommunityLayout = () => {
  return (
    <>
      <div style={{ margin: '0 auto' }}>
        <SearchBar />
      </div>
      <Outlet />
    </>
  );
};

export default CommunityLayout;
