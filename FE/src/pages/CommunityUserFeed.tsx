import styled from 'styled-components';
import { useContext } from 'react';

import Image from '@/components/common/ImageSquare';
import * as Text from '@/components/common/Text';
import Follow from '@/components/community/Follow';
import DanceGridBox from '@/components/dance/DanceGridBox';
import FeedItem from '@/components/feed/CommunityFeedItem';
import CommunityHover from '@/components/feed/CommunityHover';
import { feedContext } from '@/store/feed-context';
import FeedItemModal from '@/components/feed/FeedItemModal';

const TextMargin = styled(Text.M)`
  margin: 0 12px;
`;

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
  const { dances, modal } = useContext(feedContext);

  return (
    <Container>
      <Image size={240} src="/images/index.jpg" isRound={true} />
      <div style={{ margin: '24px 0' }}>
        <Text.XL>Dancer Name</Text.XL>
      </div>
      <Follow $size="medium" $followed={false} memberId={123} />
      <Wrapper>
        <Text.M>게시물</Text.M>
        <TextMargin>{123}</TextMargin>
        <TextMargin>팔로워</TextMargin>
        <Text.M>{123}</Text.M>
      </Wrapper>
      <DanceGridBox column={3}>
        {dances.map((dance, idx) => (
          <FeedItem key={dance.feedId} index={idx} src={dance.feedThumbnailUrl}>
            <CommunityHover likeCount={dance.likeCount} />
          </FeedItem>
        ))}
      </DanceGridBox>
      {modal.isOpen && <FeedItemModal modal={modal} />}
    </Container>
  );
};

export default CommunityUserFeedPage;

import { getFeedProfile } from '@/services/community';

export const communityFeedLoader = async (memberId: number) => {
  const response = await getFeedProfile(memberId);
  return response.data;
};
