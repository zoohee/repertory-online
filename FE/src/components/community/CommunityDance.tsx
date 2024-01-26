import styled from 'styled-components';

import Dance from '@/components/dance/Dance';
import Thumbnail from '@/components/Image';
import Like from '@/components/community/Like';
import Download from '@/components/community/Download';

const Box = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  margin-left: 8px;
  justify-content: center;
`;

const CommunityDance = ({ item }) => {
  return (
    <Dance imageUrl={item.imageUrl} title={item.title}>
      <Thumbnail size={40} isRound={true} src="images/index.jpg" />

      <Box>
        <div className="text-secondary">{item.userName}</div>
        <div className="text-secondary">구독자수 / 업로드 날짜</div>
      </Box>

      {item.isSource && <Download count={item.like} />}

      <Like clicked={item.clicked} count={item.like} />
    </Dance>
  );
};

export default CommunityDance;
