import Dance from '@/components/dance/Dance';
import Like from '@/components/community/Like';
import Download from '@/components/community/Download';
import UserProfile from '@/components/UserProfile';
import Text, { TextStyle } from '@/components/common/Text';
// import { string } from 'prop-types';
interface IMember {
  id: number;
  name: string;
  profileImage: string;
}
interface IItem {
  imageUrl: string;
  member: IMember;
  title: string;
  isSource: boolean;
  like: number;
  clicked: boolean;
}
interface IItemProps {
  item: IItem;
}
const CommunityDance = ({ item }: IItemProps) => {
  return (
    <Dance thumbnail={item.imageUrl} title={item.title}>
      <UserProfile
        imageSize={40}
        member={item.member}
        textStyle={new TextStyle('s', 's')}
      >
        <Text size='s' color='s'>
          구독자수 / 업로드 날짜
        </Text>
      </UserProfile>
      <div style={{ display: 'flex' }}>
        {item.isSource && <Download count={item.like} />}

        <Like liked={item.clicked} likeCount={item.like} disable />
      </div>
    </Dance>
  );
};

export default CommunityDance;
