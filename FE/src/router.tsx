import { createBrowserRouter } from 'react-router-dom';

import URL from '@/url';
import Index from '@/pages/Index';
import Login from '@/pages/Login';
import SignUp from '@/pages/SignUp';
import SidebarLayout from '@/pages/SidebarLayout';
import ProjectsPage from '@/pages/Projects';
import SourcesPage, { sourceLoader } from '@/pages/Sources';
import CommunityPage, { communityLoader } from '@/pages/Community';
import CommunityDetailPage, {
  communityDetailLoader,
} from '@/pages/CommunityDetail';
import CommunityLayout from '@/pages/CommunityLayout';
import CommunityUserFeedPage, {
  communityFeedLoader,
} from '@/pages/CommunityUserFeed';
import MyfeedPage from '@/pages/MyFeed';
import FollowingPage, { followingLoader } from '@/pages/Following';
import ProjectPage from '@/pages/ProjectPage';

import SourcesContextProvider from '@/store/sources-context';
import FeedContextProvider from '@/store/feed-context';
import RepertoryPage from './pages/Repertory';
import Test from './pages/Test';
const router = createBrowserRouter([
  {
    path: '/',
    element: <Index />,
  },
  {
    path: '/test',
    element: <Test />,
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
    path: '/secretdoor',
    element: <RepertoryPage />,
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
            loader: communityLoader,
          },
          {
            path: `${URL.communityDetail}/:feedId`,
            element: <CommunityDetailPage />,
            loader: ({ params }) =>
              communityDetailLoader(Number(params.feedId)),
          },
          {
            path: `${URL.communityFeed}/:memberId`,
            element: (
              <FeedContextProvider>
                <CommunityUserFeedPage />
              </FeedContextProvider>
            ),
            loader: ({ params }) =>
              communityFeedLoader(Number(params.memberId)),
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
        loader: followingLoader,
      },
    ],
  },
]);

export default router;
