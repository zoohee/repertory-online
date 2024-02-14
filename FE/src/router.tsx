import { createBrowserRouter } from 'react-router-dom';

import URL from '@/url';
import Index from '@/pages/Index';
import Login from '@/pages/Login';
import SignUp from '@/pages/SignUp';
import SidebarLayout from '@/pages/SidebarLayout';
import ProjectsPage, { projectsLoader } from '@/pages/workspace/Projects';
import SourcesPage, { sourceLoader } from '@/pages/workspace/Sources';
import CommunityPage, { communityLoader } from '@/pages/Community';
import CommunityDetailPage, {
  communityDetailLoader,
} from '@/pages/CommunityDetail';
import CommunityLayout from '@/pages/CommunityLayout';
import CommunityUserFeedPage, {
  communityFeedLoader,
} from '@/pages/CommunityUserFeed';
import MyfeedPage, { myFeedLoader } from '@/pages/my-page/MyFeed';
import FollowingPage, { followingLoader } from '@/pages/my-page/Following';
import ProjectPage from '@/pages/ProjectPage';

import SourcesContextProvider from '@/store/sources-context';
import FeedContextProvider from '@/store/feed-context';
import MyContextProvider from '@/store/my-context';

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
        loader: projectsLoader,
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
        element: (
          <MyContextProvider>
            <MyfeedPage />
          </MyContextProvider>
        ),
        loader: myFeedLoader,
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
