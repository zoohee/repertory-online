import styled from 'styled-components';
import { useLoaderData } from 'react-router-dom';

import UserProfile from '@/components/UserProfile';
import * as Text from '@/components/common/Text';
import Download from '@/components/community/Download';
import Like from '@/components/community/Like';
import Follow from '@/components/community/Follow';
import ChannelInfo from '@/components/Wrapper';
import { CommunityDetail } from '@/types';
import Video from '@/components/common/Video';
import CommunityDanceList from '@/components/community/CommunityDanceList';

const VideoBox = styled.div`
  width: 100%;
  video {
    border-radius: 10px;
  }
`;

const Title = styled(Text.XL)`
  margin: 1rem 0;
`;

const RelatedVideoTitle = styled(Text.L)`
  margin-top: 2rem;
  margin-bottom: 1rem;
`;

const Wrapper = styled.div`
  display: flex;
  align-items: center;
`;

const Box = styled.div`
  margin-left: 24px;
`;

const CommunityDetailPage = () => {
  const { feed, profile, sources } = useLoaderData() as CommunityDetail;

  return (
    <>
      <VideoBox>
        <Video src={feed.feedUrl} />
      </VideoBox>
      <Title>{feed.feedName}</Title>
      <ChannelInfo $margin="0">
        <Wrapper>
          <UserProfile
            imageSize={40}
            member={profile}
            name={<Text.M>{profile.memberName}</Text.M>}
          >
            <Text.Secondary>구독자 {profile.followerCount}명</Text.Secondary>
          </UserProfile>
          {/* TODO: 내 동영상은 구독 버튼 가리기 */}
          <Box>
            <Follow
              $size="small"
              $followed={profile.followed}
              memberId={feed.memberId}
            />
          </Box>
        </Wrapper>
        <Wrapper>
          {feed.feedType == 'SOURCE' && <Download feed={feed} />}
          <Like feed={feed} />
        </Wrapper>
      </ChannelInfo>
      {feed.feedType == 'REPERTORY' && (
        <RelatedVideoTitle>이 레퍼토리에 사용된 소스 영상</RelatedVideoTitle>
      )}
      {feed.feedType == 'SOURCE' && (
        <RelatedVideoTitle>이 소스가 사용된 레퍼토리 영상</RelatedVideoTitle>
      )}
      {sources && <CommunityDanceList dances={sources} />}
    </>
  );
};

export default CommunityDetailPage;

import { getFeedVideo } from '@/services/community';

export const communityDetailLoader = async (feedId: number) => {
  const response = await getFeedVideo(feedId);
  return response.data;
};
