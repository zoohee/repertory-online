import { useState, useContext } from 'react';
import styled from 'styled-components';

import Image from '@/components/common/ImageSquare';
import { feedContext } from '@/store/feed-context';
import { Community } from '@/types';
import CommunityHover from '@/components/feed/CommunityHover';

const ListItem = styled.li`
  position: relative;
`;

interface Props {
  index: number;
  feed: Community;
}

const FeedItem = ({ index, feed }: Props) => {
  const [isHovering, setIsHovering] = useState(false);
  const { selectDance, openModal } = useContext(feedContext);

  const handleMouseOver = () => {
    setIsHovering(true);
  };

  const handleMouseOut = () => {
    setIsHovering(false);
  };

  const handleClick = () => {
    selectDance(index);
    openModal();
  };

  return (
    <ListItem onMouseOver={handleMouseOver} onMouseOut={handleMouseOut}>
      {isHovering && (
        <CommunityHover onClick={handleClick} likeCount={feed.likeCount} />
      )}
      <Image src={feed.feedThumbnailUrl}></Image>
    </ListItem>
  );
};

export default FeedItem;
