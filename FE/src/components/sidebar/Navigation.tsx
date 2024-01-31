import styled from 'styled-components';

import TabGroup, {
  SidebarTab,
  Tab,
  TabLink,
} from '@/components/sidebar/TabGroup';
import url from '@/url';

const Tabs = styled.ul`
  width: 160px;
  *:hover {
    cursor: pointer;
  }
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
  return (
    <nav style={{ height: 'calc(48px * 7)' }}>
      <Tabs>
        <TabGroup groupName="Workspace" tabGroup={WORKSPACE} />
        <Tab>
          <TabLink path={url.community}>Community</TabLink>
        </Tab>
        <TabGroup groupName={'My Page'} tabGroup={MYPAGE} />
      </Tabs>
    </nav>
  );
};

export default Navigation;
