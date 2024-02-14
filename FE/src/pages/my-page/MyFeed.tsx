import { useContext } from 'react';

import { myContext } from '@/store/my-context';
import TabButtons from '@/components/common/Tab';
import DanceGridBox from '@/components/dance/DanceGridBox';
import FeedItem from '@/components/feed/MyFeedItem';
import FeedItemModal from '@/components/feed/FeedItemModal';

const MyfeedPage = () => {
  const { tabs, dances, modal } = useContext(myContext);

  return (
    <>
      <TabButtons margin="48px 0 24px" tabs={tabs} />
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
