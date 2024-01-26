import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

import Index from '@/pages/Index';
import SidebarPage from '@/pages/SidebarPage';

export default function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Index />} />
        <Route path="/*" element={<SidebarPage />} />
      </Routes>
    </Router>
  );
}
