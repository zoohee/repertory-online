import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';

import TabButtons, { Tab } from '@/components/common/Tab';
import DanceGridBox from '@/components/dance/DanceGridBox';
import CommunityDance from '@/components/community/CommunityDance';

const ListContainer = styled.li`
  &:hover {
    cursor: pointer;
  }
`;

const DUMMY_LIST = [
  {
    id: 1,
    imageUrl: 'images/index.jpg',
    title: 'Source #1',
    userName: 'user1',
    clicked: true,
    like: 123,
    isSource: true,
  },
  {
    id: 2,
    imageUrl: 'images/index.jpg',
    title: 'Repertory #2',
    userName: 'user2',
    clicked: false,
    like: 456,
    isSource: false,
  },
];

const TABS = [new Tab('Repertory', true), new Tab('Source', false)];

const CommunityPage = () => {
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

  const navigate = useNavigate();

  const navigateHandler = (id: number) => {
    navigate(`r/${id}`);
  };

  return (
    <>
      {/* TODO: 검색창 넣기 */}
      <TabButtons tabs={tabs} margin='0 0 24px' onClickTab={handleClickTab} />
      <DanceGridBox column={3}>
        {DUMMY_LIST.map((item, idx) => {
          return (
            <ListContainer onClick={() => navigateHandler(item.id)}>
              <CommunityDance key={idx} item={item} />
            </ListContainer>
          );
        })}
      </DanceGridBox>
    </>
  );
};

export default CommunityPage;
