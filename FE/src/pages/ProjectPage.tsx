import styled from 'styled-components';
import SourceList from '@/components/project/SourceList';
import ProjectView from '@/components/project/ProjectView';
import Workbench from '@/components/project/Workbench';
const Container = styled.div`
  display: flex;
  align-items: center;
  padding: 24px 24;
`;
const SourceWrapper = styled(Container)`
  z-index: 10;
  margin: var(--sidebar-margin);
  border-radius: 10px;
  min-height: calc(100vh - var(--sidebar-margin) * 2);
  background-color: var(--sidebar-color);
  width: var(--sidebar-project--width);
  justify-content: space-evenly;
  flex-direction: column;
`;
const EditWrapper = styled(Container)`
  z-index: 10;
  margin-right: 10px;
  /* border-radius: 10px; */
  min-height: calc(100vh - var(--sidebar-margin) * 2);
  /* background-color: var(--sidebar-color); */
  width: calc(100vw - var(--sidebar-project--width));
  justify-content: space-evenly;
  flex-direction: column;
`;

const ViewWarpper = styled.div`
  /* border-radius: 10px; */
  min-height: calc(64vh - var(--sidebar-margin) * 2);
  background-color: var(--sidebar-color);
  width: calc(100vw - var(--sidebar-project--width));
  display: flex;
  justify-content: center;
  align-items: center;
`;

const WorkbenchWarpper = styled.div`
  margin-top: 10px;
  /* border-radius: 10px; */
  min-height: calc(36vh - var(--sidebar-margin) * 2);
  background-color: var(--sidebar-color);
  width: calc(100vw - var(--sidebar-project--width));
  justify-content: space-evenly;
  flex-direction: column;
`;

const ProjectPage = () => {
  return (
    <>
      <Container>
        <SourceWrapper>
          <SourceList />
        </SourceWrapper>
        <EditWrapper>
          <ViewWarpper>
            <ProjectView />
          </ViewWarpper>
          <WorkbenchWarpper>
            <Workbench></Workbench>
          </WorkbenchWarpper>
        </EditWrapper>
      </Container>
    </>
  );
};

export default ProjectPage;
