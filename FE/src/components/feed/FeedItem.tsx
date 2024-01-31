import { ReactNode, useState } from 'react';
import styled, { css } from 'styled-components';

import Image from '@/components/common/Image';
import FeedItemModal from '@/components/feed/FeedItemModal';

const Box = styled.li`
  position: relative;
  aspect-ratio: 1 / 1;
  div {
    height: 100%;
  }
`;

const Hover = styled.div<{ hovering: boolean }>`
  position: absolute;
  ${({ hovering }) => {
    if (!hovering) {
      return css`
        visibility: hidden;
      `;
    }
  }}
`;

const FeedItem = ({ children }: { children: ReactNode }) => {
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

  return (
    <Box onMouseOver={handleMouseOver} onMouseOut={handleMouseOut}>
      <Hover onClick={handleClick} hovering={isHovering}>
        {children}
      </Hover>
      <Image src="/images/index.jpg"></Image>
      <FeedItemModal
        open={isOpen}
        onClose={() => {
          setIsOpen(false);
        }}
      />
    </Box>
  );
};

export default FeedItem;
