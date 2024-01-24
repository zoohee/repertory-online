import { Route, Routes } from 'react-router-dom';
import styled from 'styled-components';

import SideBar from '@/components/SideBar';
import Projects from '@/pages/Projects';
import Sources from '@/pages/Sources';

const Container = styled.div`
  display: flex;
  background-color: var(--rp-grey-800);
  height: 100%;
`;

const SidebarPage = () => {
  return (
    <>
      <Container>
        <SideBar />
        <Routes>
          <Route path="/projects" element={<Projects />} />
          <Route path="/sources" element={<Sources />} />
        </Routes>
      </Container>
    </>
  );
};

export default SidebarPage;
