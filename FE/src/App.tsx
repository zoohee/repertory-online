import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import Index from '@/pages/Index';
import SidebarLayout from '@/pages/SidebarLayout';
import ProjectsPage from '@/pages/Projects';
import SourcesPage from '@/pages/Sources';
import CommunityPage from '@/pages/Community';

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
        element: <CommunityPage />,
      },
    ],
  },
]);

export default function App() {
  return <RouterProvider router={router} />;
}
