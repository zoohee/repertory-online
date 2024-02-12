import { useLoaderData } from 'react-router-dom';
import styled from 'styled-components';

import UserProfile from '@/components/UserProfile';
import TabButtons from '@/components/common/Tab';
import Text, { TextStyle } from '@/components/common/Text';
import Follow from '@/components/community/Follow';
import { Member, Tab } from '@/types';

const ListItem = styled.li`
  margin-bottom: 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 480px;
`;

const TABS = [new Tab('Following', true)];

const FollowingPage = () => {
  const members = useLoaderData() as Member[];

  return (
    <>
      <TabButtons tabs={TABS} margin="48px 0 32px" />
      <ul>
        {members.map((member) => (
          <ListItem key={member.memberId}>
            <UserProfile
              imageSize={52}
              member={member}
              textStyle={new TextStyle('l', 'p')}
            >
              <Text size="m" color="s">
                구독자 수
              </Text>
            </UserProfile>
            <Follow
              $size="medium"
              $followed={true}
              memberId={member.memberId}
            />
          </ListItem>
        ))}
      </ul>
    </>
  );
};

export default FollowingPage;

import { getSubscribersList } from '@/services/community';

export const followingLoader = async () => {
  const response = await getSubscribersList();
  return response.data;
};
