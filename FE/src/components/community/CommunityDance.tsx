import Dance from '@/components/dance/Dance';
import Like from '@/components/community/Like';
import Download from '@/components/community/Download';
import UserProfile from '@/components/UserProfile';
import Text from '@/components/common/Text';

const CommunityDance = ({ item }) => {
  return (
    <Dance imageUrl={item.imageUrl} title={item.title}>
      <UserProfile imageSize={40} imageSrc="images/index.jpg">
        <Text size="s" color="s">
          {item.userName}
        </Text>
        <Text size="s" color="s">
          구독자수 / 업로드 날짜
        </Text>
      </UserProfile>

      {item.isSource && <Download count={item.like} />}

      <Like clicked={item.clicked} count={item.like} />
    </Dance>
  );
};

export default CommunityDance;
