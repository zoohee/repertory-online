import styled from 'styled-components';
import SourceList from '@/components/project/SourceList';
import ProjectView from '@/components/project/ProjectView';
import Workbench from '@/components/project/Workbench';
import { DndProvider } from 'react-dnd';
import { HTML5Backend } from 'react-dnd-html5-backend';
import { Title } from '@/components/project/Title';
const Container = styled.div`
  display: flex;
  align-items: center;
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
  border-radius: 10px;
  min-height: calc(100vh - var(--sidebar-margin) );
  /* background-color: var(--sidebar-color); */
  width: calc(100vw - var(--sidebar-project--width));
  justify-content: space-evenly;
  flex-direction: column;
`;

const ViewWarpper = styled.div`
  border-radius: 10px;
  min-height: calc(64vh - var(--sidebar-margin) * 2);
  background-color: var(--sidebar-color);
  width: calc(100vw - var(--sidebar-project--width));
  display: flex;
  height : calc(60vh - var(--sidebar-margin) * 2);
  justify-content: center;
  align-items: center;
    flex-direction: column;
`;

const WorkbenchWarpper = styled.div`
  margin-top: 10px;
  border-radius: 10px;
  min-height: calc(36vh - var(--sidebar-margin) * 2);
  background-color: var(--sidebar-color);
  width: calc(100vw - var(--sidebar-project--width));
  height : calc(30vh - var(--sidebar-margin) * 2);
  justify-content: space-evenly;
  flex-direction: column;
`;

const ProjectPage = () => {
  return (
    <>
      <DndProvider backend={HTML5Backend}>
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
      </DndProvider>
    </>
  );
};

export default ProjectPage;
