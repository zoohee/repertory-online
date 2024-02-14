import { useState, useContext } from 'react';
import styled from 'styled-components';

import Image from '@/components/common/ImageSquare';
import { feedContext } from '@/store/feed-context';

const Box = styled.li`
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
  index: number;
  src: string;
}

const FeedItem = ({ children, index, src }: Props) => {
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
    <Box onMouseOver={handleMouseOver} onMouseOut={handleMouseOut}>
      {isHovering && <Hover onClick={handleClick}>{children}</Hover>}
      <Image src={src}></Image>
    </Box>
  );
};

export default FeedItem;
