import TabButtons, { Tab } from '@/components/common/Tab';
import Wrapper from '@/components/Wrapper';
import SearchBar from '@/components/SearchBar';
import ProjectList from '@/components/dance/DanceGridBox';
import ProjectItem from '@/components/dance/Project';
import CreateButton from '@/components/dance/CreateButton';

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

const TABS = [new Tab('My projects', true)];

const ProjectsPage = () => {
  return (
    <>
      <TabButtons tabs={TABS} margin="48px 0 0" />
      <Wrapper $margin="24px 0">
        <SearchBar></SearchBar>
        <CreateButton />
      </Wrapper>
      {/* TODO: 프로젝트 생성 버튼 */}
      <ProjectList column={3}>
        {DUMMY_LIST.map((item, idx) => {
          return <ProjectItem key={idx} project={item} />;
        })}
      </ProjectList>
    </>
  );
};

export default ProjectsPage;
