import Dance from '@/components/dance/Dance';
import Like from '@/components/community/Like';
import Download from '@/components/community/Download';
import UserProfile from '@/components/UserProfile';
import Text, { TextStyle } from '@/components/common/Text';
import { Community } from '@/types';

interface Props {
  item: Community;
}

const CommunityDance = ({ item }: Props) => {
  const member = {
    id: item.memberId,
    name: item.memberName,
    profileImage: item.memberProfile,
  };
  return (
    <Dance thumbnail={'/images/index.jpg'} title={item.feedName}>
      <UserProfile
        imageSize={40}
        member={member}
        textStyle={new TextStyle('s', 's')}
      >
        <Text size="s" color="s">
          구독자수 / 업로드 날짜
        </Text>
      </UserProfile>
      <div style={{ display: 'flex' }}>
        {item.feedType === 'SOURCE' && <Download count={item.likeCount} />}

        <Like liked={false} likeCount={item.likeCount} disable />
      </div>
    </Dance>
  );
};

export default CommunityDance;
