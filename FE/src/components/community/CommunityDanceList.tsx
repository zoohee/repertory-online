import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';

import { Community } from '@/types';
import DanceGridBox from '@/components/dance/DanceGridBox';
import CommunityDance from '@/components/community/CommunityDance';
import URL from '@/url';

const ListItem = styled.li`
  &:hover {
    cursor: pointer;
  }
`;

interface Props {
  dances: Community[];
}

const CommunityDanceList = ({ dances }: Props) => {
  const navigate = useNavigate();

  const navigateHandler = (id: number) => {
    navigate(`${URL.communityDetail}/${id}`);
  };

  return (
    <DanceGridBox column={3}>
      {dances.map((item) => {
        return (
          <ListItem
            key={item.feedId}
            onClick={() => navigateHandler(item.feedId)}
          >
            <CommunityDance item={item} />
          </ListItem>
        );
      })}
    </DanceGridBox>
  );
};

export default CommunityDanceList;
