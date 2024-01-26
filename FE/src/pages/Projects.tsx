import Wrapper from '@/components/Wrapper';
import ListNavigator, { Navigation } from '@/components/ListNavigator';
import SearchBar from '@/components/SearchBar';
import ProjectList from '@/components/dance/DanceList';
import Dance from '@/components/dance/Dance';

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
      <ProjectList column={3}>
        {DUMMY_LIST.map((item, idx) => {
          return (
            <Dance key={idx} imageUrl={item.imageUrl} title={item.title}>
              <p className="grey-300">{item.detail}</p>
            </Dance>
          );
        })}
      </ProjectList>
    </>
  );
};

export default ProjectsPage;
