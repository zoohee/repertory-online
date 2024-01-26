import Wrapper from '@/components/Wrapper';
import ListNavigator, { Navigation } from '@/components/ListNavigator';
import SearchBar from '@/components/SearchBar';
import ProjectList from '@/components/DanceList';

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

const NAVIGATION = [new Navigation('My projects', true)];

const ProjectsPage = () => {
  return (
    <>
      <ListNavigator navItems={NAVIGATION} />
      <Wrapper margin="24px">
        <SearchBar></SearchBar>
        {/* TODO: 프로젝트 생성 버튼 */}
      </Wrapper>
      <ProjectList list={DUMMY_LIST} column={3} />
    </>
  );
};

export default ProjectsPage;
