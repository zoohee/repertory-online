import { Route, Routes } from 'react-router-dom';
import Login from './Login';
import SignUp from './SignUp';
import Header from '../components/Header';
import Footer from '../components/Footer';
const Home = () => {
  return (
    <>
      <Header />
      <Routes>
        <Route path='login' element={<Login />} />
        <Route path='signup' element={<SignUp />} />
      </Routes>
      <Footer />
    </>
  );
};

export default Home;
