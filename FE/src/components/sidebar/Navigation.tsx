import styled from 'styled-components';

import TabGroup, {
  SidebarTab,
  Tab,
  TabLink,
} from '@/components/sidebar/TabGroup';

const Tabs = styled.ul`
width: 160px;
  *:hover {
    cursor: pointer;
  }
`;

const WORKSPACE = [
  new SidebarTab('Projects', '/projects'),
  new SidebarTab('Sources', '/sources'),
];

const Navigation = () => {
  return (
    <nav>
      <Tabs>
        <TabGroup groupName="Workspace" tabGroup={WORKSPACE} />
        <Tab>
          <TabLink path="/community">Community</TabLink>
        </Tab>
        <TabGroup groupName={'My Page'} tabGroup={[]} />
      </Tabs>
    </nav>
  );
};

export default Navigation;
