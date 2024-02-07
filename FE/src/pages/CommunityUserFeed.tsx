import styled from 'styled-components';

import Image from '@/components/common/Image';
import Text from '@/components/common/Text';
import Follow from '@/components/community/FollowL';
import DanceGridBox from '@/components/dance/DanceGridBox';
import FeedItem from '@/components/feed/CommunityFeedItem';
import CommunityHover from '@/components/feed/CommunityHover';

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
  return (
    <Container>
      <Image size={240} src='/images/index.jpg' isRound={true} />
      <div style={{ margin: '24px 0' }}>
        <Text size='xl' color='p'>
          Dancer Name
        </Text>
      </div>
      <Follow isFollowed={false} memberId={123} />
      <Wrapper>
        <Text size='m' color='p' style={{ marginRight: '12px' }}>
          게시물
        </Text>
        <Text size='m' color='p' style={{ marginRight: '36px' }}>
          {123}
        </Text>
        <Text size='m' color='p' style={{ marginRight: '12px' }}>
          팔로워
        </Text>
        <Text size='m' color='p'>
          {123}
        </Text>
      </Wrapper>
      <DanceGridBox column={3}>
        <FeedItem>
          <CommunityHover likeCount={123} />
        </FeedItem>
        <FeedItem>
          <CommunityHover likeCount={123} />
        </FeedItem>
        <FeedItem>
          <CommunityHover likeCount={123} />
        </FeedItem>
        <FeedItem>
          <CommunityHover likeCount={123} />
        </FeedItem>
        <FeedItem>
          <CommunityHover likeCount={123} />
        </FeedItem>
        <FeedItem>
          <CommunityHover likeCount={123} />
        </FeedItem>
        <FeedItem>
          <CommunityHover likeCount={123} />
        </FeedItem>
      </DanceGridBox>
    </Container>
  );
};

export default CommunityUserFeedPage;
