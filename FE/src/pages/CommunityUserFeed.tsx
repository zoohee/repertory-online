import styled from 'styled-components';
import { useContext } from 'react';

import Image from '@/components/common/ImageSquare';
import * as Text from '@/components/common/Text';
import Follow from '@/components/community/Follow';
import DanceGridBox from '@/components/dance/DanceGridBox';
import FeedItem from '@/components/feed/CommunityFeedItem';
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

const Wrapper = styled.div<{ $size: number }>`
  display: flex;
  margin: ${({ $size }) => $size}px 0;
`;

const CommunityUserFeedPage = () => {
  const { dances, profile, modal } = useContext(feedContext);

  return (
    <Container>
      <Image size={240} src={profile.memberProfile} isRound={true} />
      <Wrapper $size={24}>
        <Text.XL>{profile.memberName}</Text.XL>
      </Wrapper>
      <Follow $size="medium" $followed={profile.followed} memberId={123} />
      <Wrapper $size={40}>
        <Text.M>POST</Text.M>
        <TextMargin>{dances.length}</TextMargin>
        <TextMargin>FOLLOW</TextMargin>
        <Text.M>{profile.followerCount}</Text.M>
      </Wrapper>
      <DanceGridBox column={3}>
        {dances.map((dance, idx) => (
          <FeedItem key={dance.feedId} index={idx} feed={dance} />
        ))}
      </DanceGridBox>
      {modal.isOpen && <FeedItemModal modal={modal} />}
    </Container>
  );
};

export default CommunityUserFeedPage;

import { getFeedProfile, getFeedList } from '@/services/community';

export const communityFeedLoader = async (memberId: number) => {
  const profile = (await getFeedProfile(memberId)).data;
  const dances = (await getFeedList(memberId)).data;
  return { dances, profile };
};
