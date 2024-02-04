import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import Index from '@/pages/Index';
import SidebarLayout from '@/pages/SidebarLayout';
import ProjectsPage from '@/pages/Projects';
import SourcesPage from '@/pages/Sources';
import CommunityPage from '@/pages/Community';
import CommunityDetailPage from '@/pages/CommunityDetail';
import CommunityLayout from '@/pages/CommunityLayout';
import CommunityUserFeedPage from '@/pages/CommunityUserFeed';
import Repertory from '@/pages/Repertory';
const router = createBrowserRouter([
  {
    path: '/',
    element: <Repertory />,
  },
  {
    path: '/',
    element: <SidebarLayout />,
    children: [
      {
        path: '/projects',
        element: <ProjectsPage />,
      },
      {
        path: '/sources',
        element: <SourcesPage />,
      },
      {
        path: '/community',
        element: <CommunityLayout />,
        children: [
          {
            index: true,
            element: <CommunityPage />,
          },
          {
            path: '/community/r/:Id',
            element: <CommunityDetailPage />,
          },
          {
            path: '/community/m/:userId',
            element: <CommunityUserFeedPage />,
          },
        ],
      },
    ],
  },
]);

export default function App() {
  return <RouterProvider router={router} />;
}
