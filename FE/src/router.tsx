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
import FollowingPage from '@/pages/Following';
import ProjectPage from '@/pages/ProjectPage';
import Repertory from '@/pages/Repertory';
// import Test from './pages/Test';
import Login from './pages/Login';
const router = createBrowserRouter([
  {
    path: '/',
    element: <Index />,
  },
  // {
  //   path: '/test',
  //   element: <Test />,
  // },
  {
    path: URL.workspace,
    element: <ProjectPage />,
  },
  {
    path: '/secretdoor',
    element: <Repertory />,
  },
  {
    path: '/secretdoor',
    element: <Repertory />,
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
      {
        path: URL.Following,
        element: <FollowingPage />,
      },
    ],
  },
]);

export default router;
