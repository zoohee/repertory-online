import styled from 'styled-components';
import { useContext } from 'react';

import { Tab } from '@/types';
import TabButtons from '@/components/common/Tab';
import SearchBar from '@/components/SearchBar';
import ProjectList from '@/components/dance/DanceGridBox';
import ProjectItem from '@/components/dance/projects/ProjectItem';
import CreateButton from '@/components/dance/CreateButton';
import { projectContext } from '@/store/project-context';

const Wrapper = styled.div`
  margin: 24px 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
`;

const TABS = [new Tab('My projects', true, () => {})];

const ProjectsPage = () => {
  const { projects } = useContext(projectContext);

  return (
    <>
      <TabButtons tabs={TABS} />
      <Wrapper>
        <SearchBar search={() => {}} />
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
