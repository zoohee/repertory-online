import { useContext } from 'react';
import styled from 'styled-components';

import { myContext } from '@/store/my-context';
import TabButtons from '@/components/common/Tab';
import DanceGridBox from '@/components/dance/DanceGridBox';
import FeedItem from '@/components/feed/MyFeedItem';
import FeedItemModal from '@/components/feed/FeedItemModal';
import Filter from '@/components/feed/Filter';

const Box = styled.div`
  width: 100%;
  position: relative;
`;

const MyfeedPage = () => {
  const { tabs, dances, modal } = useContext(myContext);

  return (
    <>
      <Box>
        <Filter />
        <TabButtons marginBottom={24} tabs={tabs} />
      </Box>
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
  // TODO: 내 멤버 아이디 토큰에서 가져오기
  const myMemberId = 5678;
  const response = await getFeedList(myMemberId);
  return response.data;
};
