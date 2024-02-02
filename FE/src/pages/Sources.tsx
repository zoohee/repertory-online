import { useState } from 'react';

import TabButtons, { Tab } from '@/components/common/Tab';
import SearchBar from '@/components/SearchBar';
import SourceList from '@/components/dance/DanceGridBox';
import SourceItem from '@/components/dance/Source';

const TABS: Tab[] = [
  new Tab('My Sources', true),
  new Tab('Cloned Sources', false),
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
  const [tabs, setTabs] = useState<Tab[]>(TABS);

  const handleClickTab = (clickedTabName: string) => {
    setTabs(
      tabs.map((tab) => {
        const clicked: boolean = tab.name == clickedTabName;
        return {
          ...tab,
          clicked,
        };
      })
    );
  };

  return (
    <>
      <TabButtons tabs={tabs} margin="48px 0 0" onClickTab={handleClickTab} />
      <SearchBar></SearchBar>
      {/* TODO: 프로젝트 생성 버튼 */}
      <SourceList column={4}>
        {DUMMY_LIST.map((item, idx) => {
          return <SourceItem key={idx} source={item} />;
        })}
      </SourceList>
    </>
  );
};

export default SourcesPage;
