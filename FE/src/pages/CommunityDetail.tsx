import styled from 'styled-components';
import { useLoaderData } from 'react-router-dom';

import UserProfile from '@/components/UserProfile';
import * as Text from '@/components/common/Text';
import Download from '@/components/community/Download';
import Like from '@/components/community/Like';
import Follow from '@/components/community/Follow';
import ChannelInfo from '@/components/Wrapper';
import { Community, Member } from '@/types';
import Video from '@/components/common/Video';

const TextLarge = styled(Text.XL)`
  margin: 16px 0;
`;

const Wrapper = styled.div`
  display: flex;
  align-items: center;
`;

const Box = styled.div`
  margin-left: 24px;
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
      <Video src={dance.feedUrl} />
      <TextLarge>{dance.feedName}</TextLarge>
      <ChannelInfo $margin="0">
        <Wrapper>
          <UserProfile
            imageSize={40}
            member={member}
            name={<Text.M>{member.memberName}</Text.M>}
          >
            <Text.Secondary>구독자 수</Text.Secondary>
          </UserProfile>
          <Box>
            <Follow $size="small" $followed={false} memberId={dance.memberId} />
          </Box>
        </Wrapper>
        <Wrapper>
          {dance.feedType == 'SOURCE' && (
            <Download count={dance.downloadCount} />
          )}

          <Like feed={dance} />
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
