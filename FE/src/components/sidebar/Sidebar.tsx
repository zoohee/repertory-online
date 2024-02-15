import styled from 'styled-components';
import { Link } from 'react-router-dom';
import HomeIcon from '@mui/icons-material/Home';

import ProfileImage from '@/components/common/ImageSquare';
import Navigation from '@/components/sidebar/Navigation';
import { S } from '@/components/common/Text';

const Text = styled(S)`
  margin: 1rem;
`;

const ColumnBox = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Profile = styled(ColumnBox)`
  padding: 24px 0;
  margin: 8px 0;
  width: 100%;
  border-top: 1px solid rgba(255, 255, 255, 0.2);
  border-bottom: 1px solid rgba(255, 255, 255, 0.2);
`;

const Aside = styled(ColumnBox)`
  z-index: 11;
  position: fixed;
  margin: var(--sidebar-margin);
  margin-right: 0;
  padding: 8px;
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

const Box = styled.div`
  display: flex;
  justify-content: flex-end;
  width: 100%;
`;

const StyledLink = styled(Link)`
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

const SideBar = () => {
  return (
    <Aside as="aside">
      <Box>
        <StyledLink to="/">
          <HomeIcon />
        </StyledLink>
      </Box>
      <Profile>
        <ProfileImage size={144} isRound={true} src="/images/index.jpg" />
        <Text>loginUserName</Text>
        <Logout>Log Out</Logout>
      </Profile>
      <Navigation />
    </Aside>
  );
};

export default SideBar;
