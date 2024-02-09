import { RouterProvider } from 'react-router-dom';

import router from '@/router';

export default function App() {
  return <RouterProvider router={router} />;
}

// import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

// import Index from '@/pages/Index';
// import SidebarPage from '@/pages/SidebarPage';

// export default function App() {
//   return (
//     <Router>
//       <Routes>
//         <Route path="/" element={<Index />} />
//         <Route path="/*" element={<SidebarPage />} />
//       </Routes>
//     </Router>
//   );
// }
