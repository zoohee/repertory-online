import { useLoaderData } from 'react-router-dom';

import CommunityDanceList from '@/components/community/CommunityDanceList';
import { Community } from '@/types';

const CommunityPage = () => {
  const dances = useLoaderData() as Community[];
  return <CommunityDanceList dances={dances} />;
};

export default CommunityPage;

import { getCommunityFeed } from '@/services/community';

export const communityLoader = async () => {
  const response = await getCommunityFeed({ page: 0, pageSize: 100 });
  return response.data;
};
