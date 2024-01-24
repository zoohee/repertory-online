import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './pages/Home';
import Project from './pages/Project';
export default function App() {
  return (
    <Router>
      <Routes>
        <Route path='/*' element={<Home />} />
        <Route path='/project' element={<Project />} />
      </Routes>
    </Router>
  );
}
