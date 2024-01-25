import styled from 'styled-components';

import SearchBar from '@/components/SearchBar';
import ProjectList from '@/components/DanceList';
import { primaryFont, fontSize } from '@/styles/font';

const Container = styled.div`
  padding: 0 32px;
  width: calc(100% - 240px);
  display: flex;
  flex-direction: column;
`;
const Header = styled.div`
  margin-top: 128;
  display: flex;
  justify-content: space-between;
`;

const Nav = styled.div`
  width: 100%;
  border-bottom: solid 1px var(--rp-grey-300);
`;

const NavItem = styled.button`
  ${primaryFont.bold}
  ${fontSize.l}
  padding: 16px;
  background-color: transparent;
  border: 0;
  border-bottom: solid 1px var(--rp-white);
`;
const Wrapper = styled.div`
  margin: 16px;
  display: flex;
  justify-content: space-between;
`;

const DUMMY_LIST = [
  {
    imageUrl: 'images/index.jpg',
    title: 'Project #1',
    detail: '2 hours ago',
  },
  {
    imageUrl: 'images/index.jpg',
    title: 'Project #2',
    detail: '7 hours ago',
  },
];

const ProjectsPage = () => {
  return (
    <Container>
      <Header>
        <Nav>
          <NavItem>My projects</NavItem>
        </Nav>
      </Header>
      <Wrapper>
        <SearchBar></SearchBar>
      </Wrapper>
      <ProjectList list={DUMMY_LIST} column={3} />
    </Container>
  );
};

export default ProjectsPage;
