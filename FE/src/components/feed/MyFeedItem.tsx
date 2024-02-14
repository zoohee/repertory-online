import { useState } from 'react';
import styled from 'styled-components';

import Image from '@/components/common/Image';
import LockIcon from '@/components/feed/Lock';
import { Community } from '@/types';
import MyFeedHover from '@/components/feed/MyFeedHover';

const ListItem = styled.li`
  position: relative;
  aspect-ratio: 1 / 1;
  & > div {
    height: 100%;
  }
`;

interface Props {
  feed: Community;
  index: number;
}

const FeedItem = ({ feed, index }: Props) => {
  const [isHovering, setIsHovering] = useState(false);
  const [isPrivate, setPrivate] = useState(feed.feedDisable);

  const handleMouseOver = () => {
    setIsHovering(true);
  };

  const handleMouseOut = () => {
    setIsHovering(false);
  };

  const lock = () => {
    setPrivate(true);
  };

  const unlock = () => {
    setPrivate(false);
  };

  return (
    <ListItem onMouseOver={handleMouseOver} onMouseOut={handleMouseOut}>
      {isHovering && (
        <MyFeedHover
          feed={feed}
          index={index}
          isPrivate={isPrivate}
          lock={lock}
          unlock={unlock}
        />
      )}
      {isPrivate && !isHovering && <LockIcon />}
      <Image src={feed.feedThumbnailUrl}></Image>
    </ListItem>
  );
};

export default FeedItem;
