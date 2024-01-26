import { useState } from 'react';

import ListNavigator, { Navigation } from '@/components/ListNavigator';
import Wrapper from '@/components/Wrapper';
import SearchBar from '@/components/SearchBar';
import SourceList from '@/components/DanceList';

const NAVIGATION: Navigation[] = [
  new Navigation('My Sources', true),
  new Navigation('Cloned Sources', false),
];

const DUMMY_LIST = [
  {
    imageUrl: 'images/index.jpg',
    title: 'Source #1',
    detail: '2 minutes ago',
  },
  {
    imageUrl: 'images/index.jpg',
    title: 'Source #2',
    detail: '7 minutes ago',
  },
];

const SourcesPage = () => {
  const [navItems, setNavItems] = useState<Navigation[]>(NAVIGATION);

  const handleClickNav = (navName: string) => {
    setNavItems(
      navItems.map((item) => {
        let clicked = false;
        if (item.name == navName) {
          clicked = true;
        }
        return {
          ...item,
          clicked,
        };
      })
    );
  };

  return (
    <>
      <ListNavigator navItems={navItems} onClickNav={handleClickNav} />
      <Wrapper margin="24px">
        <SearchBar></SearchBar>
        {/* TODO: 프로젝트 생성 버튼 */}
      </Wrapper>
      <SourceList list={DUMMY_LIST} column={4} />
    </>
  );
};

export default SourcesPage;
