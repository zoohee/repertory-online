import styled from 'styled-components';

import TabGroup, { SidebarTab } from '@/components/sidebar/TabLinkGroup';
import TabLink from '@/components/sidebar/TabLink';
import url from '@/url';

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  justify-content: space-around;
`;

const Nav = styled.nav<{ $tabCount: number }>`
  height: ${({ $tabCount }) =>
    `calc(var(--sidebar-nav-height) * ${$tabCount})`};
  width: 100%;
  padding: 0 16px;
`;

const WORKSPACE = [
  new SidebarTab('Projects', url.projects),
  new SidebarTab('Sources', url.sources),
];

const MYPAGE = [
  new SidebarTab('Feed', url.myFeed),
  new SidebarTab('Following', url.Following),
];

const Navigation = () => {
  const tabCount = 3 + WORKSPACE.length + MYPAGE.length;
  return (
    <Wrapper>
      <Nav $tabCount={tabCount}>
        <ul>
          <TabGroup groupName="Workspace" tabGroup={WORKSPACE} />
          <li>
            <TabLink path={url.community}>Community</TabLink>
          </li>
          <TabGroup groupName={'My Page'} tabGroup={MYPAGE} />
        </ul>
      </Nav>
    </Wrapper>
  );
};

export default Navigation;
