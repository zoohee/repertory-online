import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';

import DanceGridBox from '@/components/dance/DanceGridBox';
import CommunityDance from '@/components/community/CommunityDance';

const ListContainer = styled.li`
  &:hover {
    cursor: pointer;
  }
`;

const DUMMY_LIST = [
  {
    id: 1,
    imageUrl: 'images/index.jpg',
    title: 'Source #1',
    member: {
      id: 1,
      name: 'user1',
      profileImage: '/images/index.jpg',
    },
    clicked: true,
    like: 123,
    isSource: true,
  },
  {
    id: 2,
    imageUrl: 'images/index.jpg',
    title: 'Repertory #2',
    member: {
      id: 2,
      name: 'user2',
      profileImage: '/images/index.jpg',
    },
    clicked: false,
    like: 456,
    isSource: false,
  },
];

const CommunityPage = () => {
  const navigate = useNavigate();

  const navigateHandler = (id: number) => {
    navigate(`/community/r/${id}`);
  };

  return (
    <>
      {/* TODO: 검색창 넣기 */}
      <DanceGridBox column={3}>
        {DUMMY_LIST.map((item) => {
          return (
            <ListContainer key={item.id} onClick={() => navigateHandler(item.id)}>
              <CommunityDance item={item} />
            </ListContainer>
          );
        })}
      </DanceGridBox>
    </>
  );
};

export default CommunityPage;
