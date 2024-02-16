import { useContext } from 'react';
import styled from 'styled-components';

import { myContext } from '@/store/my-context';
import TabButtons from '@/components/common/Tab';
import DanceGridBox from '@/components/dance/DanceGridBox';
import FeedItem from '@/components/feed/MyFeedItem';
import FeedItemModal from '@/components/feed/FeedItemModal';
import Filter from '@/components/feed/Filter';
import { decodeJwt } from '@/services/util';

const FilterBox = styled.div`
  margin-bottom: 12px;
  height: 2rem;
  width: 100%;
  position: relative;
`;

const MyfeedPage = () => {
  const { tabs, dances, modal } = useContext(myContext);

  return (
    <>
      <TabButtons marginBottom={12} tabs={tabs} />
      <FilterBox>
        <Filter />
      </FilterBox>
      <DanceGridBox column={3}>
        {dances.map((dance, idx) => (
          <FeedItem key={dance.feedId} feed={dance} index={idx} />
        ))}
      </DanceGridBox>
      {modal.isOpen && <FeedItemModal modal={modal} disable />}
    </>
  );
};

export default MyfeedPage;

import { getFeedList } from '@/services/community';

export const myFeedLoader = async () => {
  const myMemberId = decodeJwt();
  if (myMemberId === -1) {
    return [];
  }
  const response = await getFeedList(myMemberId);
  return response.data;
};
