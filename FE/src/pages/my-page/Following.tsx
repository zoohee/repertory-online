import { useLoaderData } from 'react-router-dom';
import styled from 'styled-components';

import UserProfile from '@/components/UserProfile';
import TabButtons from '@/components/common/Tab';
import * as Text from '@/components/common/Text';
import Follow from '@/components/community/Follow';
import { Member, Tab } from '@/types';

const Detail = styled(Text.M)`
  color: var(--text-secondary-dark-mode);
  margin-top: 4px;
`;

const ListItem = styled.li`
  margin-bottom: 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 480px;
`;

const TABS = [new Tab('Following', true, () => {})];

const FollowingPage = () => {
  const members = useLoaderData() as Member[];

  return (
    <>
      <TabButtons tabs={TABS} marginBottom={32} />
      <ul>
        {members.map((member) => (
          <ListItem key={member.memberId}>
            <UserProfile
              imageSize={52}
              member={member}
              name={<Text.L>{member.memberName}</Text.L>}
            >
              <Detail>구독자 수</Detail>
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
