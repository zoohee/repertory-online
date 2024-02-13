import { useState } from 'react';
import styled from 'styled-components';

import Image from '@/components/common/Image';
import FeedItemModal from '@/components/feed/FeedItemModal';
import Lock from '@/components/feed/Lock';
import Like from '@/components/community/Like';
import { Community } from '@/types';

const ListItem = styled.li`
  position: relative;
  aspect-ratio: 1 / 1;
  & > div {
    height: 100%;
  }
`;

const Box = styled.span`
  position: absolute;
  bottom: 16px;
  right: 16px;
  z-index: 2;
  text-shadow: 0 1px 2px var(--rp-grey-500);
`;

const Hover = styled.div`
  position: absolute;
  height: 100%;
  width: 100%;
`;

interface Props {
  children: React.ReactNode;
  feed: Community;
}

const FeedItem = ({ children, feed }: Props) => {
  const [isHovering, setIsHovering] = useState(false);
  const [isOpen, setIsOpen] = useState(false);

  const handleMouseOver = () => {
    setIsHovering(true);
  };

  const handleMouseOut = () => {
    setIsHovering(false);
  };

  const handleClick = () => {
    setIsOpen((prev) => !prev);
  };

  const isPrivate: boolean = true;

  return (
    <ListItem onMouseOver={handleMouseOver} onMouseOut={handleMouseOut}>
      {isHovering && <Hover onClick={handleClick}>{children}</Hover>}
      {isPrivate && <Lock />}
      {!isHovering && (
        <Box>
          <Like feed={feed} disable />
        </Box>
      )}
      <Image src="/images/index.jpg"></Image>
      <FeedItemModal
        open={isOpen}
        onClose={() => {
          setIsOpen(false);
        }}
      />
    </ListItem>
  );
};

export default FeedItem;
