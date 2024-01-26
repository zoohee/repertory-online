import DanceList from '@/components/dance/DanceList';
import CommunityDance from '@/components/community/CommunityDance';

const DUMMY_LIST = [
  {
    imageUrl: 'images/index.jpg',
    title: 'Source #1',
    userName: 'user1',
    clicked: true,
    like: 123,
    isSource: true,
  },
  {
    imageUrl: 'images/index.jpg',
    title: 'Repertory #2',
    userName: 'user2',
    clicked: false,
    like: 456,
    isSource: false,
  },
];

const CommunityPage = () => {
  return (
    <>
      {/* TODO: 검색창 넣기 */}
      <DanceList column={3}>
        {DUMMY_LIST.map((item, idx) => {
          return <CommunityDance key={idx} item={item} />;
        })}
      </DanceList>
    </>
  );
};

export default CommunityPage;
