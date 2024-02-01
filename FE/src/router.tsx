import { createBrowserRouter } from 'react-router-dom';

import URL from '@/url';

import Index from '@/pages/Index';
import SidebarLayout from '@/pages/SidebarLayout';
import ProjectsPage from '@/pages/Projects';
import SourcesPage from '@/pages/Sources';
import CommunityPage from '@/pages/Community';
import CommunityDetailPage from '@/pages/CommunityDetail';
import CommunityLayout from '@/pages/CommunityLayout';
import CommunityUserFeedPage from '@/pages/CommunityUserFeed';
import MyfeedPage from '@/pages/MyFeed';

const router = createBrowserRouter([
  {
    path: '/',
    element: <Index />,
  },
  {
    path: '/',
    element: <SidebarLayout />,
    children: [
      {
        path: URL.projects,
        element: <ProjectsPage />,
      },
      {
        path: URL.sources,
        element: <SourcesPage />,
      },
      {
        path: URL.community,
        element: <CommunityLayout />,
        children: [
          {
            index: true,
            element: <CommunityPage />,
          },
          {
            path: `${URL.communityDetail}/:Id`,
            element: <CommunityDetailPage />,
          },
          {
            path: `${URL.communityFeed}/:userId`,
            element: <CommunityUserFeedPage />,
          },
        ],
      },
      {
        path: URL.myFeed,
        element: <MyfeedPage />,
      },
    ],
  },
]);

export default router;
