import { useNavigate, useLoaderData } from 'react-router-dom';
import styled from 'styled-components';

import DanceGridBox from '@/components/dance/DanceGridBox';
import CommunityDance from '@/components/community/CommunityDance';
import { Community } from '@/types';
import URL from '@/url';

const ListContainer = styled.li`
  &:hover {
    cursor: pointer;
  }
`;

const CommunityPage = () => {
  const communityDanceList = useLoaderData() as Community[];

  const navigate = useNavigate();

  const navigateHandler = (id: number) => {
    navigate(`${URL.communityDetail}/${id}`);
  };

  return (
    <>
      <DanceGridBox column={3}>
        {communityDanceList.map((item) => {
          return (
            <ListContainer key={item.feedId} onClick={() => navigateHandler(item.feedId)}>
              <CommunityDance item={item} />
            </ListContainer>
          );
        })}
      </DanceGridBox>
    </>
  );
};

export default CommunityPage;

import { getCommunityFeed } from '@/services/community';

export const communityLoader = async () => {
  const response = await getCommunityFeed({ page: 0, pageSize: 5 });
  return response.data;
}
