import styled from 'styled-components';
import { useLoaderData } from 'react-router-dom';

import { Project, Tab } from '@/types';
import TabButtons from '@/components/common/Tab';
import SearchBar from '@/components/SearchBar';
import ProjectList from '@/components/dance/DanceGridBox';
import ProjectItem from '@/components/dance/Project';
import CreateButton from '@/components/dance/CreateButton';

const Wrapper = styled.div`
  margin: 24px 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
`;

const TABS = [new Tab('My projects', true, () => {})];

const ProjectsPage = () => {
  const projects = useLoaderData() as Project[];

  return (
    <>
      <TabButtons tabs={TABS} />
      <Wrapper>
        <SearchBar />
        <CreateButton to="/workspace" target="_blank" />
      </Wrapper>
      <ProjectList column={3}>
        {projects.map((item, idx) => {
          return <ProjectItem key={idx} project={item} />;
        })}
      </ProjectList>
    </>
  );
};

export default ProjectsPage;

import { getProjectsList } from '@/services/project';

export const projectsLoader = async () => {
  const response = await getProjectsList();
  return response.data;
};
