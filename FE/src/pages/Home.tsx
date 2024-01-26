import { Route, Routes } from 'react-router-dom';

import Login from '@/pages/Login';
import SignUp from '@/pages/SignUp';
import Index from '@/pages/Index';

const Home = () => {
  return (
    <>
      <Routes>
        <Route path="/" element={<Index />} />
        <Route path="login" element={<Login />} />
        <Route path="signup" element={<SignUp />} />
      </Routes>
    </>
  );
};

export default Home;
