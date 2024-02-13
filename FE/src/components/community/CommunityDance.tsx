import Dance from '@/components/dance/Dance';
import Like from '@/components/community/Like';
import Download from '@/components/community/Download';
import UserProfile from '@/components/UserProfile';
import { Secondary as Text, M } from '@/components/common/Text';
import { Community, Member } from '@/types';
import styled from 'styled-components';

const Name = styled(M)`
  color: var(--text-secondary-dark-mode);
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
      <div style={{ display: 'flex' }}>
        {item.feedType === 'SOURCE' && <Download count={item.downloadCount} />}

        <Like liked={false} likeCount={item.likeCount} disable />
      </div>
    </Dance>
  );
};

export default CommunityDance;
