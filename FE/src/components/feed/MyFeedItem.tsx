import { useState, useContext } from 'react';
import styled from 'styled-components';

import Image from '@/components/common/Image';
import Lock from '@/components/feed/Lock';
import { Community } from '@/types';
import { myContext } from '@/store/my-context';

const ListItem = styled.li`
  position: relative;
  aspect-ratio: 1 / 1;
  & > div {
    height: 100%;
  }
`;

const Hover = styled.div`
  position: absolute;
  height: 100%;
  width: 100%;
`;

interface Props {
  children: React.ReactNode;
  feed: Community;
  index: number;
}

const FeedItem = ({ children, feed, index }: Props) => {
  const [isHovering, setIsHovering] = useState(false);
  const { openModal, selectDance } = useContext(myContext);

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
      {isHovering && <Hover onClick={handleClick}>{children}</Hover>}
      {feed.feedDisable && !isHovering && <Lock />}
      <Image src={feed.feedThumbnailUrl}></Image>
    </ListItem>
  );
};

export default FeedItem;
