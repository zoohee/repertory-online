import Dance from '@/components/dance/Dance';
import Like from '@/components/community/Like';
import Download from '@/components/community/Download';
import UserProfile from '@/components/UserProfile';
import Text, { TextStyle } from '@/components/common/Text';

const CommunityDance = ({ item }) => {
  return (
    <Dance thumbnail={item.imageUrl} title={item.title}>
      <UserProfile
        imageSize={40}
        member={item.member}
        textStyle={new TextStyle('s', 's')}
      >
        <Text size="s" color="s">
          구독자수 / 업로드 날짜
        </Text>
      </UserProfile>

      {item.isSource && <Download count={item.like} />}

      <Like liked={item.clicked} likeCount={item.like} />
    </Dance>
  );
};

export default CommunityDance;
