import Wrapper from '@/components/Wrapper';
import TabButtons, { Tab } from '@/components/common/Tab';
import SearchBar from '@/components/SearchBar';
import ProjectList from '@/components/dance/DanceGridBox';
import Dance from '@/components/dance/Dance';
import Button from '@/components/common/Button';
import { Link } from 'react-router-dom';
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
      <TabButtons tabs={TABS} margin='48px 0 0' />
      <SearchBar></SearchBar>
      <Link to='/workspace' target='_blank' style={{ textDecoration: 'none' }}>
        <Button buttonText='NewProject' btnsize='s' />
      </Link>
      {/* TODO: 프로젝트 생성 버튼 */}
      <ProjectList column={3}>
        {DUMMY_LIST.map((item, idx) => {
          return (
            <Dance key={idx} thumbnail={item.imageUrl} title={item.title}>
              <p className='text-secondary'>{item.detail}</p>
            </Dance>
          );
        })}
      </ProjectList>
    </>
  );
};

export default ProjectsPage;
