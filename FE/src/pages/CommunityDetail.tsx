import styled from 'styled-components';

import UserProfile from '@/components/UserProfile';
import Text, { TextStyle } from '@/components/common/Text';
import Download from '@/components/community/Download';
import Like from '@/components/community/Like';
import Follow from '@/components/community/FollowS';
import ChannelInfo from '@/components/Wrapper';

const Video = styled.div`
  padding: 200px 200px;
  height: 480px;
  border: solid 1px white;
`;

const Wrapper = styled.div`
  display: flex;
  align-items: center;
`;

const CommunityDetailPage = () => {
  // TODO: axios로 받아오기
  const dance = {
    title: 'Dance Title',
    isSource: false,
    like: 123,
    clicked: false,
    member: {
      id: 2,
      name: 'user2',
      profileImage: '/images/index.jpg',
    },
  };

  return (
    <>
      <Video>영상 자리</Video>
      <Text size="xl" color="p" style={{ margin: '16px 0' }}>
        {dance.title}
      </Text>
      <ChannelInfo margin="0">
        <Wrapper>
          <UserProfile
            imageSize={40}
            member={dance.member}
            textStyle={new TextStyle('m', 'p')}
          >
            <Text size="s" color="s">
              구독자 수
            </Text>
          </UserProfile>
          <Follow isFollowed={false} />
        </Wrapper>
        <Wrapper>
          {dance.isSource && <Download count={dance.like} />}

          <Like liked={dance.clicked} likeCount={dance.like} />
        </Wrapper>
      </ChannelInfo>
    </>
  );
};

export default CommunityDetailPage;
