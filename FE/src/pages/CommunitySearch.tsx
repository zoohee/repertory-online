import { useLoaderData, useParams } from 'react-router-dom';
import styled from 'styled-components';

import CommunityDanceList from '@/components/community/CommunityDanceList';
import { Community } from '@/types';
import * as Text from '@/components/common/Text';

const Box = styled.div`
  height: 300px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Title = styled(Text.L)`
  margin-bottom: 1rem;
`;

const NoContent = styled(Text.M)`
  color: var(--text-secondary-dark-mode);
`;

const CommunitySearch = () => {
  const dances = useLoaderData() as Community[];
  const { keyword } = useParams();
  const result = () => {
    if (dances && dances.length > 0) {
      return (
        <>
          <Title>{`'${keyword}'(으)로 검색한 결과`}</Title>
          <CommunityDanceList dances={dances} />
        </>
      );
    }
    return (
      <Box>
        <NoContent>검색 결과가 없습니다</NoContent>
      </Box>
    );
  };

  return <>{result()}</>;
};

export default CommunitySearch;

import { getFeedSearch } from '@/services/community';

export const communitySearchLoader = async (keyword: string | undefined) => {
  if (!keyword) {
    return [];
  }
  const response = await getFeedSearch(keyword);
  return response.data;
};
