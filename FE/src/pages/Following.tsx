import UserProfile from '@/components/UserProfile';
import Wrapper from '@/components/Wrapper';
import TabButtons, { Tab } from '@/components/common/Tab';
import Text, { TextStyle } from '@/components/common/Text';
import Follow from '@/components/community/FollowL';

const TABS = [new Tab('Following', true)];

const member = {
  id: 1,
  name: '김싸피',
  profileImage: '/images/index.jpg',
};

const FollowingPage = () => {
  return (
    <>
      <TabButtons tabs={TABS} margin='48px 0 24px' />
      <ul>
        <Wrapper as='li' $margin='0 0 12px' style={{ width: '480px' }}>
          <UserProfile
            imageSize={52}
            member={member}
            textStyle={new TextStyle('l', 'p')}
          >
            <Text size='m' color='s'>
              구독자 수
            </Text>
          </UserProfile>
          <Follow isFollowed={true} memberId={123} />
        </Wrapper>
      </ul>
    </>
  );
};

export default FollowingPage;
