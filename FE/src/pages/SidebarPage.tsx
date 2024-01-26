import { Route, Routes } from 'react-router-dom';
import styled from 'styled-components';

import SideBar from '@/components/SideBar';
import Projects from '@/pages/Projects';
import Sources from '@/pages/Sources';
import Community from '@/pages/Community';

const Container = styled.div`
  display: flex;
  background-color: var(--rp-grey-800);
  min-height: 100%;
`;

const Main = styled.main`
  padding: 0 32px;
  min-width: calc(100% - 240px);
  display: flex;
  flex-direction: column;
`;

const SidebarPage = () => {
  return (
    <Container>
      <SideBar />
      <Main>
        <Routes>
          <Route path="/projects" element={<Projects />} />
          <Route path="/sources" element={<Sources />} />
          <Route path="/community" element={<Community />} />
        </Routes>
      </Main>
    </Container>
  );
};

export default SidebarPage;
