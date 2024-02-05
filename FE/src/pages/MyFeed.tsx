import { useState } from 'react';

import TabButtons, { Tab } from '@/components/common/Tab';
import DanceGridBox from '@/components/dance/DanceGridBox';
import FeedItem from '@/components/feed/MyFeedItem';
import MyFeedHover from '@/components/feed/MyFeedHover';

const TABS = [
  new Tab('Feed', true),
  new Tab('Repertory', false),
  new Tab('Source', false),
];

const MyfeedPage = () => {
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
      <TabButtons margin="48px 0 24px" tabs={tabs} onClickTab={handleClickTab} />
      <DanceGridBox column={3}>
        <FeedItem>
          <MyFeedHover />
        </FeedItem>
      </DanceGridBox>
    </>
  );
};

export default MyfeedPage;
