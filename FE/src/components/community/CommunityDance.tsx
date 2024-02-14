import styled from 'styled-components';

import Dance from '@/components/dance/Dance';
import Like from '@/components/community/Like';
import Download from '@/components/community/Download';
import UserProfile from '@/components/UserProfile';
import { Secondary as Text, M } from '@/components/common/Text';
import { Community, Member } from '@/types';

const Name = styled(M)`
  color: var(--text-secondary-dark-mode);
`;

const Box = styled.div`
  display: flex;
`;

interface Props {
  item: Community;
}

const CommunityDance = ({ item }: Props) => {
  const member: Member = {
    memberId: item.memberId,
    memberName: item.memberName,
    memberProfile: item.memberProfile,
  };
  return (
    <Dance thumbnail={item.feedThumbnailUrl} title={item.feedName}>
      <UserProfile
        imageSize={40}
        member={member}
        name={<Name>{member.memberName}</Name>}
      >
        <Text>{item.feedDate}</Text>
      </UserProfile>
      <Box>
        {item.feedType === 'SOURCE' && <Download feed={item} disable />}
        <Like feed={item} disable />
      </Box>
    </Dance>
  );
};

export default CommunityDance;
