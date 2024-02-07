import TabGroup, { SidebarTab } from '@/components/sidebar/TabLinkGroup';
import TabLink from '@/components/sidebar/TabLink';
import url from '@/url';

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
  const navHeight = `calc(var(--sidebar-nav-height) * ${tabCount})`;
  return (
    <nav style={{ height: `${navHeight}`, width: '100%', padding: '0 16px' }}>
      <ul>
        <TabGroup groupName="Workspace" tabGroup={WORKSPACE} />
        <li>
          <TabLink path={url.community}>Community</TabLink>
        </li>
        <TabGroup groupName={'My Page'} tabGroup={MYPAGE} />
      </ul>
    </nav>
  );
};

export default Navigation;
