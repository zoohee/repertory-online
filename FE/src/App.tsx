import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import Index from '@/pages/Index';
import SidebarLayout from '@/pages/SidebarLayout';
import ProjectsPage from '@/pages/Projects';
import SourcesPage from '@/pages/Sources';
import CommunityPage from '@/pages/Community';
import CommunityDetailPage from '@/pages/CommunityDetail';
import CommunityLayout from './pages/CommunityLayout';

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
            path: '/community',
            element: <CommunityPage />,
          },
          {
            path: '/community/r/:Id',
            element: <CommunityDetailPage />,
          },
        ],
      },
    ],
  },
]);

export default function App() {
  return <RouterProvider router={router} />;
}
