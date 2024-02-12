import styled from 'styled-components';
import { useContext } from 'react';

import Image from '@/components/common/Image';
import Text from '@/components/common/Text';
import Follow from '@/components/community/Follow';
import DanceGridBox from '@/components/dance/DanceGridBox';
import FeedItem from '@/components/feed/CommunityFeedItem';
import CommunityHover from '@/components/feed/CommunityHover';
import { feedContext } from '@/store/feed-context';
import FeedItemModal from '@/components/feed/FeedItemModal';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Wrapper = styled.div`
  display: flex;
  margin: 24px 0;
`;

const CommunityUserFeedPage = () => {
  const { dances, isModalOpen } = useContext(feedContext);

  return (
    <Container>
      <Image size={240} src="/images/index.jpg" isRound={true} />
      <div style={{ margin: '24px 0' }}>
        <Text size="xl" color="p">
          Dancer Name
        </Text>
      </div>
      <Follow $size="medium" $followed={false} memberId={123} />
      <Wrapper>
        <Text size="m" color="p" style={{ marginRight: '12px' }}>
          게시물
        </Text>
        <Text size="m" color="p" style={{ marginRight: '36px' }}>
          {123}
        </Text>
        <Text size="m" color="p" style={{ marginRight: '12px' }}>
          팔로워
        </Text>
        <Text size="m" color="p">
          {123}
        </Text>
      </Wrapper>
      <DanceGridBox column={3}>
        {dances.map((dance, idx) => (
          <FeedItem key={dance.feedId} index={idx} src={dance.feedThumbnailUrl}>
            <CommunityHover likeCount={dance.likeCount} />
          </FeedItem>
        ))}
      </DanceGridBox>
      {isModalOpen && <FeedItemModal />}
    </Container>
  );
};

export default CommunityUserFeedPage;

import { getFeedProfile } from '@/services/community';

export const communityFeedLoader = async (memberId: number) => {
  const response = await getFeedProfile(memberId);
  return response.data;
};
