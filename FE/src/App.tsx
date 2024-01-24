import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from '@/pages/Home';
import SidebarPage from '@/pages/SidebarPage';

export default function App() {
  return (
    <Router>
      <Routes>
        <Route path='/*' element={<Home />} />
        <Route path='/projects' element={<SidebarPage />} />
        <Route path='/sources' element={<SidebarPage />} />
      </Routes>
    </Router>
  );
}
