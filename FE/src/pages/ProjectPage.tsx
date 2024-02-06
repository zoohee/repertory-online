import styled from 'styled-components';
const Container = styled.div`
  display: flex;
  align-items: center;
  padding: 24px 24;
`;
const Source = styled(Container)`
  z-index: 10;
  margin: var(--sidebar-margin);
  border-radius: 10px;
  min-height: calc(100vh - var(--sidebar-margin) * 2);
  background-color: var(--sidebar-color);
  width: var(--sidebar-project--width);
  justify-content: space-evenly;
`;
const Edit = styled(Container)`
  z-index: 10;
  margin-right: 10px;
  border-radius: 10px;
  min-height: calc(100vh - var(--sidebar-margin) * 2);
  background-color: var(--sidebar-color);
  width: calc(100vw - var(--sidebar-project--width));
  justify-content: space-evenly;
`;

const ProjectPage = () => {
  return (
    <>
      <Container>
        <Source as='aside'></Source>
        <Edit>hi</Edit>
      </Container>
    </>
  );
};

export default ProjectPage;
