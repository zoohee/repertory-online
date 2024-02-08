import { createBrowserRouter } from 'react-router-dom';

import URL from '@/url';

import Index from '@/pages/Index';
import Login from '@/pages/Login';
import SignUp from '@/pages/SignUp';
import SidebarLayout from '@/pages/SidebarLayout';
import ProjectsPage from '@/pages/Projects';
import SourcesPage, { sourceLoader } from '@/pages/Sources';
import CommunityPage from '@/pages/Community';
import CommunityDetailPage from '@/pages/CommunityDetail';
import CommunityLayout from '@/pages/CommunityLayout';
import CommunityUserFeedPage from '@/pages/CommunityUserFeed';
import MyfeedPage from '@/pages/MyFeed';
import FollowingPage from '@/pages/Following';
import ProjectPage from '@/pages/ProjectPage';

import SourcesContextProvider from '@/store/sources-context';

const router = createBrowserRouter([
  {
    path: '/',
    element: <Index />,
  },
  {
    path: URL.login,
    element: <Login />,
  },
  {
    path: URL.signUp,
    element: <SignUp />,
  },
  {

    path: URL.workspace,
    element: <ProjectPage />,
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
        element: (
          <SourcesContextProvider>
            <SourcesPage />
          </SourcesContextProvider>
        ),
        loader: sourceLoader,
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
