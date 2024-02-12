import styled from 'styled-components';
import { useLoaderData } from 'react-router-dom';

import UserProfile from '@/components/UserProfile';
import Text, { TextStyle } from '@/components/common/Text';
import Download from '@/components/community/Download';
import Like from '@/components/community/Like';
import Follow from '@/components/community/FollowS';
import ChannelInfo from '@/components/Wrapper';
import { Community, Member } from '@/types';

const Video = styled.video`
  width: 100%;
  max-height: calc(100vh * 0.5);
`;

const Wrapper = styled.div`
  display: flex;
  align-items: center;
`;

const CommunityDetailPage = () => {
  const dance = useLoaderData() as Community;
  const member: Member = {
    memberId: dance.memberId,
    memberName: dance.memberName,
    memberProfile: dance.memberProfile,
  };
  return (
    <>
      <Video controls autoPlay muted loop crossOrigin="anonymous" >
        <source src={dance.feedUrl} type="video/mp4" />
      </Video>
      <Text size="xl" color="p" style={{ margin: '16px 0' }}>
        {dance.feedName}
      </Text>
      <ChannelInfo $margin="0">
        <Wrapper>
          <UserProfile
            imageSize={40}
            member={member}
            textStyle={new TextStyle('m', 'p')}
          >
            <Text size="s" color="s">
              구독자 수
            </Text>
          </UserProfile>
          <div style={{ marginLeft: '24px' }}>
            <Follow isFollowed={false} memberId={dance.memberId} />
          </div>
        </Wrapper>
        <Wrapper>
          {dance.feedType == 'SOURCE' && <Download count={dance.downloadCount} />}

          <Like liked={false} likeCount={dance.likeCount} />
        </Wrapper>
      </ChannelInfo>
    </>
  );
};

export default CommunityDetailPage;

import { getFeedVideo } from '@/services/community';

export const communityDetailLoader = async (feedId: number) => {
  const response = await getFeedVideo(feedId);
  return response.data;
};
